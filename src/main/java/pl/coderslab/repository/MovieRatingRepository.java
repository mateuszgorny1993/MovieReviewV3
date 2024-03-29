package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.MovieRating;
import java.util.List;
@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    List<MovieRating> findByMovieId(Long movieId);
}
