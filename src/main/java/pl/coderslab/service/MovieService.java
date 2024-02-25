package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.dto.MovieRatingDto;
import pl.coderslab.model.Movie;
import pl.coderslab.model.MovieRating;
import pl.coderslab.model.OmdbMovieDetails;
import pl.coderslab.repository.MovieRatingRepository;
import pl.coderslab.repository.MovieRepository;

import java.util.List;

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

    public void updateMovieWithOmdbData(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new IllegalArgumentException("Movie not found for ID: " + movieId));

        String imdbId = movie.getImdbId();
        if (imdbId != null && !imdbId.trim().isEmpty()) {
            OmdbMovieDetails omdbMovieDetails = omdbApiService.fetchMovieDetails(imdbId);
            if (omdbMovieDetails != null && omdbMovieDetails.getImdbRating() != null) {
                try {
                    movie.setOcenaOmdb(Double.parseDouble(omdbMovieDetails.getImdbRating()));
                    movieRepository.save(movie);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing IMDB rating: " + omdbMovieDetails.getImdbRating());
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



}
