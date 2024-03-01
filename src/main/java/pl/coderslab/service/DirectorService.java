package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.DirectorRatingDto;
import pl.coderslab.model.Director;
import pl.coderslab.model.DirectorRating;
import pl.coderslab.repository.DirectorRatingRepository;
import pl.coderslab.repository.DirectorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorRatingRepository directorRatingRepository;

    public DirectorService(DirectorRepository directorRepository, DirectorRatingRepository directorRatingRepository) {
        this.directorRepository = directorRepository;
        this.directorRatingRepository = directorRatingRepository;
    }

    public Optional<Director> getDirectorDetails(Long id) {
        return directorRepository.findByIdWithMovies(id);
    }

    public Page<Director> getApprovedDirectors(int page, int size, String sortType) {
        Sort sort = switch (sortType) {
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views");
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC, "views");
            default -> Sort.by(Sort.Direction.ASC, "lastName");
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        return directorRepository.findByIsApprovedTrue(pageable);
    }

    public void addOrUpdateDirectorRating(DirectorRatingDto directorRatingDto) {
        Director director = directorRepository.findById(directorRatingDto.getDirectorId())
                .orElseThrow(() -> new IllegalArgumentException("Director not found for ID: " + directorRatingDto.getDirectorId()));

        DirectorRating directorRating = new DirectorRating();
        directorRating.setDirector(director);
        directorRating.setRating(directorRatingDto.getRating());
        directorRatingRepository.save(directorRating);

        updateDirectorRatingAverage(director.getId());
    }

    private void updateDirectorRatingAverage(Long directorId) {
        List<DirectorRating> ratings = directorRatingRepository.findByDirectorId(directorId);
        double average = ratings.stream()
                .mapToDouble(DirectorRating::getRating)
                .average()
                .orElse(0.0);
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new IllegalArgumentException("Director not found for ID: " + directorId));
        director.setRating(average);
        directorRepository.save(director);
    }

    public void incrementDirectorViews(Long directorId) {
        Director director = directorRepository.findById(directorId)
                .orElseThrow(() -> new IllegalArgumentException("Director not found for ID: " + directorId));
        director.setViews(director.getViews() + 1);
        directorRepository.save(director);
    }
    public Director findByFirstNameAndLastName(String firstName, String lastName) {
        return directorRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseGet(() -> {
                    Director newDirector = new Director();
                    newDirector.setFirstName(firstName);
                    newDirector.setLastName(lastName);
                    newDirector.setRating(0.0);
                    newDirector.setViews(0);
                    return directorRepository.save(newDirector);
                });
    }
    public void save(Director director) {
        directorRepository.save(director);
    }

}
