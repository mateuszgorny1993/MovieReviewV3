package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Actor;

import java.util.Optional;


@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Page<Actor> findByIsApprovedTrue(Pageable pageable);
    Optional<Actor> findByIdAndIsApprovedTrue(Long id);
}
