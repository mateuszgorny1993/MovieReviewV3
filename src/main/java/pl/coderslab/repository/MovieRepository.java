package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByIsApprovedTrue(Pageable pageable);
    @Query("SELECT m FROM Movie m WHERE m.releaseDate > CURRENT_DATE AND m.isApproved = true")
    Page<Movie> findUpcomingMovies(Pageable pageable);
}
