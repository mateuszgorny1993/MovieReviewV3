package pl.coderslab.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.dto.ActorRatingDto;
import pl.coderslab.dto.DirectorRatingDto;
import pl.coderslab.dto.MovieRatingDto;
import pl.coderslab.model.Actor;
import pl.coderslab.model.Director;
import pl.coderslab.model.Movie;
import pl.coderslab.service.ActorService;
import pl.coderslab.service.DirectorService;
import pl.coderslab.service.MovieService;

import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/guest")
public class GuestController {
    private final MovieService movieService;
    private final ActorService actorService;
    private final DirectorService directorService;

    public GuestController(MovieService movieService, ActorService actorService, DirectorService directorService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
    }

    @GetMapping("/movies")
    public String listMovies(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "title") String sortType,
            Model model) {
        Page<Movie> moviesPage = movieService.getApprovedMovies(page, size, sortType);
        model.addAttribute("moviesPage", moviesPage);
        model.addAttribute("currentSort", sortType);
        return "movies";
    }

    @GetMapping("/movies/details/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model) {
        movieService.getMovieDetails(id).ifPresent(movie -> {
            movieService.incrementMovieViews(movie.getId());
            movieService.updateMovieWithOmdbData(movie.getId());
            model.addAttribute("movie", movie);
        });
        List<Movie> similarMovies = movieService.getSimilarMoviesLimited(id);
        model.addAttribute("similarMovies", similarMovies);

        return "movieDetails";
    }

    @PostMapping("/ratemovie")
    public String rateMovie(MovieRatingDto movieRatingDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || movieRatingDto.getRating() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Proszę wybrać ocenę.");
            return "redirect:/guest/movies/details/" + movieRatingDto.getMovieId();
        }
        movieService.addOrUpdateMovieRating(movieRatingDto);
        redirectAttributes.addFlashAttribute("message", "Dziękujemy za ocenę filmu!");
        return "redirect:/guest/movies/details/" + movieRatingDto.getMovieId();
    }

    @GetMapping("/actors")
    public String listActors(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "lastName") String sortType,
            Model model) {
        Page<Actor> actorsPage = actorService.getApprovedActors(page, size, sortType);
        model.addAttribute("actorsPage", actorsPage);
        model.addAttribute("currentSort", sortType);
        return "actors";
    }

    @GetMapping("/actors/details/{id}")
    public String showActorDetails(@PathVariable Long id, Model model) {
        actorService.getActorDetails(id).ifPresent(actor -> {
            actorService.incrementActorViews(actor.getId());
            model.addAttribute("actor", actor);
        });
        List<Actor> relatedActors = actorService.getRelatedActorsLimited(id);
        model.addAttribute("relatedActors", relatedActors);

        return "actorDetails";
    }


    @PostMapping("/rateactor")
    public String rateActor(ActorRatingDto actorRatingDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || actorRatingDto.getRating() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Proszę wybrać ocenę.");
            return "redirect:/guest/actors/details/" + actorRatingDto.getActorId();
        }
        actorService.addOrUpdateActorRating(actorRatingDto);
        redirectAttributes.addFlashAttribute("message", "Dziękujemy za ocenę aktora!");
        return "redirect:/guest/actors/details/" + actorRatingDto.getActorId();
    }


    @GetMapping("/directors")
    public String listDirectors(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "lastName") String sortType,
            Model model) {
        Page<Director> directorPage = directorService.getApprovedDirectors(page, size, sortType);
        model.addAttribute("directorsPage", directorPage);
        model.addAttribute("currentSort", sortType);
        return "directors";
    }

    @GetMapping("/directors/details/{id}")
    public String showDirectorDetails(@PathVariable Long id, Model model) {
        directorService.incrementDirectorViews(id);
        Director director = directorService.getDirectorDetails(id)
                .orElseThrow(() -> new IllegalArgumentException("Director not found for ID: " + id));
        model.addAttribute("director", director);

        Set<Actor> relatedActors = actorService.findActorsByDirector(director);
        model.addAttribute("relatedActors", relatedActors);

        return "directorDetails";
    }
    @PostMapping("/ratedirector")
    public String rateDirector(@ModelAttribute DirectorRatingDto directorRatingDto, RedirectAttributes redirectAttributes) {
        directorService.addOrUpdateDirectorRating(directorRatingDto);
        redirectAttributes.addFlashAttribute("message", "Dziękujemy za ocenę reżysera!");
        return "redirect:/guest/directors/details/" + directorRatingDto.getDirectorId();
    }



    @GetMapping("/new")
    public String showNewReleases(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "title") String sortType,
            Model model) {
        Page<Movie> newReleasesPage = movieService.getUpcomingMovies(page, size, sortType);
        model.addAttribute("newReleasesPage", newReleasesPage);
        model.addAttribute("currentSort", sortType);
        return "news";
    }


}
