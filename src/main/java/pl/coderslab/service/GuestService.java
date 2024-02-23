package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Actor;
import pl.coderslab.model.Director;
import pl.coderslab.model.Movie;
import pl.coderslab.repository.ActorRepository;
import pl.coderslab.repository.DirectorRepository;
import pl.coderslab.repository.MovieRepository;

import java.util.Optional;

@Service
public class GuestService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;

    public GuestService(MovieRepository movieRepository, ActorRepository actorRepository, DirectorRepository directorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
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

    public Page<Actor> getApprovedActors(int page, int size, String sortType) {
        Sort sort = switch (sortType) {
            case "ratingDesc" -> Sort.by(Sort.Direction.DESC, "rating");
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views");
            case "ratingAsc" -> Sort.by(Sort.Direction.ASC, "rating");
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC, "views");
            default -> Sort.by(Sort.Direction.ASC, "lastName");
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        return actorRepository.findByIsApprovedTrue(pageable);
    }

    public Page<Director> getApprovedDirectors(int page, int size, String sortType) {
        Sort sort = switch (sortType) {
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views");
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC, "views");
            default -> Sort.by(Sort.Direction.ASC, "lastName");
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        return directorRepository.findByIsApprovedTrue(pageable);
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

    public Optional<Movie> getMovieDetails(Long id) {
        return movieRepository.findByIdWithActorsAndTrailersAndCategoriesAndIsApprovedTrue(id);
    }

    public Optional<Actor> getActorDetails(Long id) {
        return actorRepository.findByIdAndIsApprovedTrue(id);
    }

    public Optional<Director> getDirectorDetails(Long id) {
        return directorRepository.findByIdAndIsApprovedTrue(id);
    }

}
