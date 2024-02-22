package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.coderslab.model.Actor;
import pl.coderslab.repository.ActorRepository;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public Page<Actor> getApprovedActors(int page, int size, String sortType) {
        Sort.by(Sort.Direction.ASC, "lastName");
        Sort sort = switch (sortType) {
            case "ratingDesc" -> Sort.by(Sort.Direction.DESC, "rating"); // Sortowanie malejąco wg oceny
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views"); // Sortowanie malejąco wg wyświetleń
            case "ratingAsc" -> Sort.by(Sort.Direction.ASC,"rating"); //Sortowanie rosnąco wg oceny
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC,"views"); //Sortowanie rosnąco wg wyświetleń
            default -> Sort.by(Sort.Direction.ASC, "lastName"); // Domyślne sortowanie alfabetyczne rosnąco
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        return actorRepository.findByIsApprovedTrue(pageable);
    }
}
