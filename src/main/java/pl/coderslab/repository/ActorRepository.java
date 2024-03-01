package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Actor;
import pl.coderslab.model.Director;
import pl.coderslab.model.Movie;

import java.util.Optional;
import java.util.Set;


@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Page<Actor> findByIsApprovedTrue(Pageable pageable);
    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    @Query("SELECT a FROM Actor a LEFT JOIN FETCH a.movies WHERE a.id = :id")
    Optional<Actor> findByIdWithMovies(@Param("id") Long id);

    @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m IN (:movies) AND a.id <> :actorId")
    Set<Actor> findRelatedActorsByMoviesAndActorId(@Param("movies") Set<Movie> movies, @Param("actorId") Long actorId);

    @Query("SELECT DISTINCT a FROM Actor a JOIN a.movies m WHERE m.director = :director")
    Set<Actor> findActorsByDirector(@Param("director") Director director);

}
