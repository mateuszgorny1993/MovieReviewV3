package pl.coderslab.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.coderslab.dto.ActorRatingDto;
import pl.coderslab.dto.DirectorRatingDto;
import pl.coderslab.dto.MovieRatingDto;
import pl.coderslab.model.*;
import pl.coderslab.service.*;

import javax.validation.Valid;
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
            if (!existingMovies.isEmpty()) {
                Movie existingMovie = existingMovies.get(0);
                return "redirect:/app/movies/details/" + existingMovie.getId();
            }
            Movie movie = new Movie();
            movie.setImdbId(movieDetails.getImdbId());
            movie.setTitle(movieDetails.getTitle());
            movie.setDescription(movieDetails.getDescription());
            movie.setDuration(Integer.parseInt(movieDetails.getDuration().replaceAll("\\D+", "")));
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

                    actorService.save(actor);
                }
                actors.add(actor);
            });

            movie.setActors(actors);
            movieService.save(movie);

            notificationService.notifyModeratorsAboutNewMovie(movie.getTitle());

            return "redirect:/app/movies/details/" + movie.getId();
        } catch (Exception e) {
            model.addAttribute("error", "Nie udało się dodać filmu: " + e.getMessage());
            return "appAddMovie";
        }
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
        return "appActors";
    }
    @GetMapping("/actors/details/{id}")
    public String showActorDetails(@PathVariable Long id, Model model) {
        actorService.getActorDetails(id).ifPresent(actor -> {
            actorService.incrementActorViews(actor.getId());
            model.addAttribute("actor", actor);
        });
        List<Actor> relatedActors = actorService.getRelatedActorsLimited(id);
        model.addAttribute("relatedActors", relatedActors);

        return "appActorDetails";
    }

    @PostMapping("/rateactor")
    public String rateActor(ActorRatingDto actorRatingDto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors() || actorRatingDto.getRating() == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Proszę wybrać ocenę.");
            return "redirect:/app/actors/details/" + actorRatingDto.getActorId();
        }
        actorService.addOrUpdateActorRating(actorRatingDto);
        redirectAttributes.addFlashAttribute("message", "Dziękujemy za ocenę aktora!");
        return "redirect:/app/actors/details/" + actorRatingDto.getActorId();
    }
    @GetMapping("/addActor")
    public String showActorAddForm(Model model) {
        model.addAttribute("actor", new Actor());
        return "appAddActor";
    }
    @PostMapping("/addActor")
    public String processActorAddForm(@Valid @ModelAttribute("actor") Actor actor, BindingResult result) {
        if (result.hasErrors()) {
            return "appAddActor";
        }
        actorService.save(actor);
        return "redirect:/app/actors/details/" + actor.getId();
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
        return "appDirectors";
    }

    @GetMapping("/directors/details/{id}")
    public String showDirectorDetails(@PathVariable Long id, Model model) {
        directorService.incrementDirectorViews(id);
        Director director = directorService.getDirectorDetails(id)
                .orElseThrow(() -> new IllegalArgumentException("Director not found for ID: " + id));
        model.addAttribute("director", director);

        Set<Actor> relatedActors = actorService.findActorsByDirector(director);
        model.addAttribute("relatedActors", relatedActors);

        return "appDirectorDetails";
    }
    @PostMapping("/ratedirector")
    public String rateDirector(@ModelAttribute DirectorRatingDto directorRatingDto, RedirectAttributes redirectAttributes) {
        directorService.addOrUpdateDirectorRating(directorRatingDto);
        redirectAttributes.addFlashAttribute("message", "Dziękujemy za ocenę reżysera!");
        return "redirect:/app/directors/details/" + directorRatingDto.getDirectorId();
    }
    @GetMapping("/addDirector")
    public String showDirectorAddForm(Model model) {
        model.addAttribute("director", new Director());
        return "appAddDirector";
    }
    @PostMapping("/addDirector")
    public String processDirectorAddForm(@Valid @ModelAttribute("director") Director director, BindingResult result) {
        if (result.hasErrors()) {
            return "appAddDirector";
        }
        directorService.save(director);
        return "redirect:/app/directors/details/" + director.getId();
    }
}
