package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.ActorRating;

import java.util.List;

@Repository
public interface ActorRatingRepository extends JpaRepository<ActorRating, Long> {
    List<ActorRating> findByActorId(Long actorId);
}
