package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.model.Movie;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByIsApprovedTrue(Pageable pageable);
    @Query("SELECT m FROM Movie m WHERE m.releaseDate > CURRENT_DATE AND m.isApproved = true")
    Page<Movie> findUpcomingMovies(Pageable pageable);
    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.actors LEFT JOIN FETCH m.trailers LEFT JOIN FETCH m.categories WHERE m.id = :id AND m.isApproved = true")
    Optional<Movie> findByIdWithActorsAndTrailersAndCategoriesAndIsApprovedTrue(@Param("id") Long id);

}
