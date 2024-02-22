package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByIsApprovedTrue(Pageable pageable);
}
