package pl.coderslab.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.model.Actor;
import pl.coderslab.model.Director;
import pl.coderslab.model.Movie;
import pl.coderslab.service.GuestService;

@Controller
@RequestMapping("/guest")
public class GuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }
    @GetMapping("/movies")
    public String listMovies(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "title") String sortType,
            Model model) {
        Page<Movie> moviesPage = guestService.getApprovedMovies(page, size, sortType);
        model.addAttribute("moviesPage", moviesPage);
        model.addAttribute("currentSort", sortType);
        return "movies";
    }
    @GetMapping("/actors")
    public String listActors(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "lastName") String sortType,
            Model model) {
        Page<Actor> actorsPage = guestService.getApprovedActors(page, size, sortType);
        model.addAttribute("actorsPage", actorsPage);
        model.addAttribute("currentSort", sortType);
        return "actors";
    }
    @GetMapping("/directors")
    public String listDirectors(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "lastName") String sortType,
            Model model) {
        Page<Director> directorPage = guestService.getApprovedDirectors(page, size, sortType);
        model.addAttribute("directorsPage",directorPage);
        model.addAttribute("currentSort", sortType);
        return "directors";
    }
    @GetMapping("/new")
    public String showNewReleases(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "title") String sortType,
            Model model) {
        Page<Movie> newReleasesPage = guestService.getUpcomingMovies(page, size,sortType);
        model.addAttribute("newReleasesPage", newReleasesPage);
        model.addAttribute("currentSort", sortType);
        return "news";
    }

    @GetMapping("/movies/details/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model) {
        guestService.getMovieDetails(id).ifPresent(movie -> model.addAttribute("movie", movie));
        return "movieDetails";
    }
    @GetMapping("/actors/details/{id}")
    public String showActorDetails(@PathVariable Long id, Model model) {
        guestService.getActorDetails(id).ifPresent(actor -> model.addAttribute("actor",actor));
        return "actorDetails";
    }
    @GetMapping("/directors/details/{id}")
    public String showDirectorDetails(@PathVariable Long id, Model model) {
        guestService.getDirectorDetails(id).ifPresent(director -> model.addAttribute("director",director));
        return "directorDetails";
    }

}
