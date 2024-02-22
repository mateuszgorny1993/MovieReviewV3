package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Movie;
import pl.coderslab.repository.MovieRepository;


@Service
public class MovieService {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Page<Movie> getApprovedMovies(int page, int size, String sortType) {
        Sort.by(Sort.Direction.ASC, "title");
        Sort sort = switch (sortType) {
            case "ratingDesc" -> Sort.by(Sort.Direction.DESC, "rating"); // Sortowanie malejąco wg oceny
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views"); // Sortowanie malejąco wg wyświetleń
            case "ratingAsc" -> Sort.by(Sort.Direction.ASC,"rating"); //Sortowanie rosnąco wg oceny
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC,"views"); //Sortowanie rosnąco wg wyświetleń
            default -> Sort.by(Sort.Direction.ASC, "title"); // Domyślne sortowanie alfabetyczne rosnąco
        };

        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findByIsApprovedTrue(pageable);
    }
}
