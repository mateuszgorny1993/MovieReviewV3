package pl.coderslab.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.dto.MovieRatingDto;
import pl.coderslab.model.Actor;
import pl.coderslab.model.Director;
import pl.coderslab.model.Movie;
import pl.coderslab.service.GuestService;
import pl.coderslab.service.MovieService;

import java.util.List;


@Controller
@RequestMapping("/guest")
public class GuestController {
    private final GuestService guestService;
    private final MovieService movieService;

    public GuestController(GuestService guestService, MovieService movieService) {
        this.guestService = guestService;
        this.movieService = movieService;
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
        model.addAttribute("directorsPage", directorPage);
        model.addAttribute("currentSort", sortType);
        return "directors";
    }

    @GetMapping("/new")
    public String showNewReleases(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "title") String sortType,
            Model model) {
        Page<Movie> newReleasesPage = guestService.getUpcomingMovies(page, size, sortType);
        model.addAttribute("newReleasesPage", newReleasesPage);
        model.addAttribute("currentSort", sortType);
        return "news";
    }


    @GetMapping("/movies/details/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model) {
        guestService.getMovieDetails(id).ifPresent(movie -> {
            movieService.incrementMovieViews(movie.getId());
            movieService.updateMovieWithOmdbData(movie.getId());
            model.addAttribute("movie", movie);
        });
        List<Movie> similarMovies = guestService.getSimilarMoviesLimited(id);
        model.addAttribute("similarMovies", similarMovies);

        return "movieDetails";
    }



    @GetMapping("/actors/details/{id}")
    public String showActorDetails(@PathVariable Long id, Model model) {
        guestService.getActorDetails(id).ifPresent(actor -> model.addAttribute("actor", actor));
        return "actorDetails";
    }

    @GetMapping("/directors/details/{id}")
    public String showDirectorDetails(@PathVariable Long id, Model model) {
        guestService.getDirectorDetails(id).ifPresent(director -> model.addAttribute("director", director));
        return "directorDetails";
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



}
