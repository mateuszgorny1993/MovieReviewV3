package pl.coderslab.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.dto.MovieRatingDto;
import pl.coderslab.model.*;
import pl.coderslab.service.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/app")
public class ApplicationController {

    private final MovieService movieService;

    private final DirectorService directorService;

    private final ActorService actorService;

    private final NotificationService notificationService;
    private final OmdbApiService omdbApiService;
    private final CategoryService categoryService;

    public ApplicationController(MovieService movieService, CategoryService categoryService, DirectorService directorService, ActorService actorService, NotificationService notificationService, OmdbApiService omdbApiService) {
        this.movieService = movieService;
        this.categoryService = categoryService;
        this.directorService = directorService;
        this.actorService = actorService;
        this.notificationService = notificationService;
        this.omdbApiService = omdbApiService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            System.out.println("Zalogowany użytkownik: " + username);
            model.addAttribute("username", username);
        } else {
            System.out.println("Użytkownik nie jest zalogowany.");
        }
        return "dashboard";
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
        return "appMovies";
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

        return "appMovieDetails";
    }

    @PostMapping("/ratemovie")
    public String rateMovie(MovieRatingDto movieRatingDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || movieRatingDto.getRating() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Proszę wybrać ocenę.");
            return "redirect:/app/movies/details/" + movieRatingDto.getMovieId();
        }
        movieService.addOrUpdateMovieRating(movieRatingDto);
        redirectAttributes.addFlashAttribute("message", "Dziękujemy za ocenę filmu!");
        return "redirect:/app/movies/details/" + movieRatingDto.getMovieId();
    }

    @GetMapping("/addMovie")
    public String addMovie() {
        return "appAddMovie";
    }

    @GetMapping("/addMovie/addByOmdbApi")
    public String addMovieByOmdbApi() {
        return "appAddMovieByOmdbApi";
    }

    @PostMapping("/addMovie/addByOmdbApi")
    public String downloadMovieDataFromOmdbApi(@RequestParam("imdbId") String imdbId, Model model) {
        OmdbMovieDetails movieDetails = omdbApiService.fetchMovieDetails(imdbId);
        model.addAttribute("movieDetails", movieDetails);
        return "appAddMovieByOmdbApiDataToVerify";
    }

    @PostMapping("/addMovie/addByOmdbApiAdd")
    @Transactional
    public String addMovieFromOmdb(@ModelAttribute OmdbMovieDetails movieDetails, Model model) {
        try {
            List<Movie> existingMovies = movieService.findByImdbId(movieDetails.getImdbId());
            if(!existingMovies.isEmpty()) {
                Movie existingMovie = existingMovies.get(0);
                return "redirect:/app/movies/details/" + existingMovie.getId();
            }
            Movie movie = new Movie();
            movie.setImdbId(movieDetails.getImdbId());
            movie.setTitle(movieDetails.getTitle());
            movie.setDescription(movieDetails.getDescription());
            movie.setDuration(Integer.parseInt(movieDetails.getDuration().replaceAll("\\D+", "")));
            movie.setRating(0.0);
            movie.setViews(0);
            movie.setOcenaOmdb(Double.parseDouble(movieDetails.getOcenaOmdb()));
            movie.setPosterPath(movieDetails.getPosterPath());

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate releaseDate = LocalDate.parse(movieDetails.getReleaseDate(), inputFormatter);
            movie.setReleaseDate(LocalDate.parse(releaseDate.format(outputFormatter)));

            String[] directorNameParts = movieDetails.getDirector().split(" ", 2);
            String firstName = directorNameParts[0];
            String lastName = directorNameParts.length > 1 ? directorNameParts[1] : "";
            Director director = directorService.findByFirstNameAndLastName(firstName, lastName);
            if (director == null) {
                director = new Director();
                director.setFirstName(firstName);
                director.setLastName(lastName);

                director.setRating(0.0);
                director.setViews(0);
                director.setApproved(true);

                directorService.save(director);
            }
            movie.setDirector(director);

            Set<Category> categories = new HashSet<>();
            Arrays.stream(movieDetails.getCategories().split(", ")).forEach(categoryName -> {
                Category category = categoryService.findByName(categoryName);
                if (category == null) {
                    category = new Category();
                    category.setName(categoryName);

                    categoryService.save(category);
                }
                categories.add(category);
            });
            movie.setCategories(categories);

            Set<Actor> actors = new HashSet<>();
            Arrays.stream(movieDetails.getActors().split(", ")).forEach(fullActorName -> {
                String[] actorNameParts = fullActorName.split(" ", 2);
                String actorFirstName = actorNameParts[0];
                String actorLastName = actorNameParts.length > 1 ? actorNameParts[1] : "";
                Actor actor = actorService.findByFirstNameAndLastName(actorFirstName, actorLastName);
                if (actor == null) {
                    actor = new Actor();
                    actor.setFirstName(actorFirstName);
                    actor.setLastName(actorLastName);

                    actor.setViews(0);
                    actor.setRating(0.0);
                    actor.setApproved(true);

                    actorService.save(actor);
                }
                actors.add(actor);
            });

            movie.setActors(actors);
            movie.setApproved(true);
            movieService.save(movie);

            notificationService.notifyModeratorsAboutNewMovie(movie.getTitle());

            return "redirect:/app/movies/details/" + movie.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Nie udało się dodać filmu: " + e.getMessage());
            return "appAddMovie";
        }
    }


}
