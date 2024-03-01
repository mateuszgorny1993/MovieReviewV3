package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.MovieRatingDto;
import pl.coderslab.model.Movie;
import pl.coderslab.model.MovieRating;
import pl.coderslab.model.OmdbMovieDetails;
import pl.coderslab.repository.MovieRatingRepository;
import pl.coderslab.repository.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {


    private final MovieRepository movieRepository;
    private final MovieRatingRepository movieRatingRepository;

    private final OmdbApiService omdbApiService;

    public MovieService(MovieRepository movieRepository, OmdbApiService omdbApiService, MovieRatingRepository movieRatingRepository) {
        this.movieRepository = movieRepository;
        this.omdbApiService = omdbApiService;
        this.movieRatingRepository = movieRatingRepository;
    }

    public Optional<Movie> getMovieDetails(Long id) {
        return movieRepository.findByIdWithActorsAndTrailersAndCategoriesAndIsApprovedTrue(id);
    }

    public Page<Movie> getUpcomingMovies(int page, int size, String sortType) {
        Sort sort = switch (sortType) {
            case "releaseDateDesc" -> Sort.by(Sort.Direction.DESC, "releaseDate");
            case "releaseDateAsc" -> Sort.by(Sort.Direction.ASC, "releaseDate");
            default -> Sort.by(Sort.Direction.ASC, "title");
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findUpcomingMovies(pageable);
    }

    public Page<Movie> getApprovedMovies(int page, int size, String sortType) {
        Sort sort = switch (sortType) {
            case "ratingDesc" -> Sort.by(Sort.Direction.DESC, "rating"); // Sortowanie malejąco wg oceny
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views"); // Sortowanie malejąco wg wyświetleń
            case "ratingAsc" -> Sort.by(Sort.Direction.ASC, "rating"); //Sortowanie rosnąco wg oceny
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC, "views"); //Sortowanie rosnąco wg wyświetleń
            default -> Sort.by(Sort.Direction.ASC, "title"); // Domyślne sortowanie alfabetyczne rosnąco
        };

        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findByIsApprovedTrue(pageable);
    }

    public void updateMovieWithOmdbData(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("Movie not found for ID: " + movieId));

        String imdbId = movie.getImdbId();
        if (imdbId != null && !imdbId.trim().isEmpty()) {
            OmdbMovieDetails omdbMovieDetails = omdbApiService.fetchMovieDetails(imdbId);
            if (omdbMovieDetails != null && omdbMovieDetails.getOcenaOmdb() != null) {
                try {
                    movie.setOcenaOmdb(Double.parseDouble(omdbMovieDetails.getOcenaOmdb()));
                    movie.setPosterPath(omdbMovieDetails.getPosterPath());
                    movieRepository.save(movie);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing IMDB rating: " + omdbMovieDetails.getOcenaOmdb());
                }
            }
        }
    }

    public void incrementMovieViews(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("Movie not found for ID: " + movieId));
        movie.setViews(movie.getViews() + 1);
        movieRepository.save(movie);
    }

    public void addOrUpdateMovieRating(MovieRatingDto movieRatingDto) {
        Movie movie = movieRepository.findById(movieRatingDto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found for ID: " + movieRatingDto.getMovieId()));

        MovieRating movieRating = new MovieRating();
        movieRating.setMovie(movie);
        movieRating.setRating(movieRatingDto.getRating());
        movieRatingRepository.save(movieRating);

        updateMovieRatingAverage(movie.getId());
    }

    private void updateMovieRatingAverage(Long movieId) {
        List<MovieRating> ratings = movieRatingRepository.findByMovieId(movieId);
        double average = ratings.stream().mapToDouble(MovieRating::getRating).average().orElse(0.0);
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("Movie not found for ID: " + movieId));
        movie.setRating(average);
        movieRepository.save(movie);
    }

    public Set<Movie> getSimilarMovies(Long movieId) {
        Optional<Movie> movieOptional = getMovieDetails(movieId);
        if (movieOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Set<Movie> similarMovies = new LinkedHashSet<>();
            similarMovies.addAll(movieRepository.findSimilarByCategory(movie.getCategories(), movie.getId()));
            similarMovies.addAll(movieRepository.findSimilarByActors(movie.getActors(), movie.getId()));
            return similarMovies;
        }
        return Collections.emptySet();
    }

    public List<Movie> getSimilarMoviesLimited(Long movieId) {
        Set<Movie> similarMovies = getSimilarMovies(movieId);
        return similarMovies.stream()
                .sorted(Comparator.comparing(Movie::getRating).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }


    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    public List<Movie> findByImdbId(String imdbId) {
        return movieRepository.findByImdbId(imdbId);
    }
}
