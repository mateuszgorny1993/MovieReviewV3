package pl.coderslab.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.ActorRatingDto;
import pl.coderslab.model.Actor;
import pl.coderslab.model.ActorRating;
import pl.coderslab.model.Director;
import pl.coderslab.model.Movie;
import pl.coderslab.repository.ActorRatingRepository;
import pl.coderslab.repository.ActorRepository;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final ActorRatingRepository actorRatingRepository;

    public ActorService(ActorRepository actorRepository, ActorRatingRepository actorRatingRepository) {
        this.actorRepository = actorRepository;
        this.actorRatingRepository = actorRatingRepository;
    }

    public Optional<Actor> getActorDetails(Long id) {
        return actorRepository.findByIdWithMovies(id);
    }

    public Page<Actor> getApprovedActors(int page, int size, String sortType) {
        Sort sort = switch (sortType) {
            case "ratingDesc" -> Sort.by(Sort.Direction.DESC, "rating");
            case "viewsDesc" -> Sort.by(Sort.Direction.DESC, "views");
            case "ratingAsc" -> Sort.by(Sort.Direction.ASC, "rating");
            case "viewsAsc" -> Sort.by(Sort.Direction.ASC, "views");
            default -> Sort.by(Sort.Direction.ASC, "lastName");
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        return actorRepository.findByIsApprovedTrue(pageable);
    }

    public void incrementActorViews(Long actorId) {
        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new IllegalArgumentException("Actor not found for ID: " + actorId));
        actor.setViews(actor.getViews() + 1);
        actorRepository.save(actor);
    }

    private void updateActorRatingAverage(Long actorId) {
        List<ActorRating> ratings = actorRatingRepository.findByActorId(actorId);
        double average = ratings.stream()
                .mapToDouble(ActorRating::getRating)
                .average()
                .orElse(0.0);
        Actor actor = actorRepository.findById(actorId).orElseThrow(
                () -> new IllegalArgumentException("Actor not found for ID: " + actorId));
        actor.setRating(average);
        actorRepository.save(actor);
    }

    public void addOrUpdateActorRating(ActorRatingDto actorRatingDto) {
        Actor actor = actorRepository.findById(actorRatingDto.getActorId())
                .orElseThrow(() -> new IllegalArgumentException("Actor not found for ID: " + actorRatingDto.getActorId()));

        ActorRating actorRating = new ActorRating();
        actorRating.setActor(actor);
        actorRating.setRating(actorRatingDto.getRating());
        actorRatingRepository.save(actorRating);

        updateActorRatingAverage(actor.getId());
    }

    public Set<Actor> getRelatedActors(Long actorId) {
        Optional<Actor> actorOptional = actorRepository.findById(actorId);
        if (actorOptional.isPresent()) {
            Actor actor = actorOptional.get();
            Set<Movie> movies = actor.getMovies();
            return actorRepository.findRelatedActorsByMoviesAndActorId(movies, actorId);
        }
        return Collections.emptySet();
    }

    public List<Actor> getRelatedActorsLimited(Long actorId) {
        Set<Actor> relatedActors = getRelatedActors(actorId);
        return relatedActors.stream()
                .limit(5)
                .collect(Collectors.toList());
    }

    public Set<Actor> findActorsByDirector(Director director) {
        return actorRepository.findActorsByDirector(director);
    }

    public Actor findByFirstNameAndLastName(String firstName, String lastName) {
        return actorRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseGet(() -> {
                    Actor newActor = new Actor();
                    newActor.setFirstName(firstName);
                    newActor.setLastName(lastName);
                    return actorRepository.save(newActor);
                });
    }

    public void save(Actor actor) {
        actorRepository.save(actor);
    }

}
