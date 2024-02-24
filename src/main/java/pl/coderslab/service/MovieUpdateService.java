package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.model.Movie;
import pl.coderslab.model.OmdbMovieDetails;
import pl.coderslab.repository.MovieRepository;

@Service
public class MovieUpdateService {


    private final MovieRepository movieRepository;

    private final OmdbApiService omdbApiService;

    public MovieUpdateService(MovieRepository movieRepository, OmdbApiService omdbApiService) {
        this.movieRepository = movieRepository;
        this.omdbApiService = omdbApiService;
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
}
