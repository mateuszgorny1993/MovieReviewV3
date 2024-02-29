package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.DirectorRating;

import java.util.List;

@Repository
public interface DirectorRatingRepository extends JpaRepository<DirectorRating, Long> {
    List<DirectorRating> findByDirectorId(Long directorId);
}
