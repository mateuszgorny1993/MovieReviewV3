package pl.coderslab.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

}
