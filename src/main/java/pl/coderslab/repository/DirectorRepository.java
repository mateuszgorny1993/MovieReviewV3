package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.model.Actor;
import pl.coderslab.model.Director;

import java.util.Optional;
import java.util.Set;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Page<Director> findByIsApprovedTrue(Pageable pageable);

    @Query("SELECT d FROM Director d LEFT JOIN FETCH d.movies WHERE d.id = :id")
    Optional<Director> findByIdWithMovies(@Param("id") Long id);

}

