package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.MovieRating;
import java.util.List;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    List<MovieRating> findByMovieId(Long movieId);
}
