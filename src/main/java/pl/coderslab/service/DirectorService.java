package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Director;
import pl.coderslab.repository.DirectorRepository;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public Page<Director> getApprovedDirectors(int page, int size, String sortType) {
        Sort.by(Sort.Direction.ASC, "lastName");
        Sort sort = switch (sortType) {
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views"); // Sortowanie malejąco wg wyświetleń
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC,"views"); //Sortowanie rosnąco wg wyświetleń
            default -> Sort.by(Sort.Direction.ASC, "lastName"); // Domyślne sortowanie alfabetyczne rosnąco
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        return directorRepository.findByIsApprovedTrue(pageable);
    }
}

