package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.model.Trailer;

import java.util.List;

@Repository
public interface TrailerRepository extends JpaRepository<Trailer, Long> {
    List<Trailer> findByMovieId(Long movieId);
}

