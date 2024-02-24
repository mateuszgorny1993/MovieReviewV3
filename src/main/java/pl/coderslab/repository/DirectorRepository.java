package pl.coderslab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.model.Director;

import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    Page<Director> findByIsApprovedTrue(Pageable pageable);

    Optional<Director> findByIdAndIsApprovedTrue(Long id);
}

