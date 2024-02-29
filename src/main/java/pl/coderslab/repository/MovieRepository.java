package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Actor;
import pl.coderslab.model.Category;
import pl.coderslab.model.Movie;

import java.util.List;
import java.util.Optional;
import java.util.Set;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Page<Movie> findByIsApprovedTrue(Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.releaseDate > CURRENT_DATE AND m.isApproved = true")
    Page<Movie> findUpcomingMovies(Pageable pageable);

    @Query("SELECT m FROM Movie m LEFT JOIN FETCH m.actors LEFT JOIN FETCH m.trailers LEFT JOIN FETCH m.categories WHERE m.id = :id AND m.isApproved = true")
    Optional<Movie> findByIdWithActorsAndTrailersAndCategoriesAndIsApprovedTrue(@Param("id") Long id);

    @Query("SELECT m FROM Movie m JOIN m.categories c WHERE c IN (:categories) AND m.id <> :movieId AND m.isApproved = true")
    List<Movie> findSimilarByCategory(@Param("categories") Set<Category> categories, @Param("movieId") Long movieId);

    @Query("SELECT m FROM Movie m JOIN m.actors a WHERE a IN (:actors) AND m.id <> :movieId AND m.isApproved = true")
    List<Movie> findSimilarByActors(@Param("actors") Set<Actor> actors, @Param("movieId") Long movieId);


}
