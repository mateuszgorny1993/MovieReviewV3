package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.model.Movie;
import pl.coderslab.service.MovieService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/app/admin")
public class AdminController {
    MovieService movieService;

    public AdminController(MovieService movieService) {
        this.movieService = movieService;
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
        return "adminDashboard";
    }
    @GetMapping("/moviesToConfirm")
    public String listMoviesToConfirm(Model model) {
        List<Movie> movies = movieService.findAllByIsApproved(false);
        model.addAttribute("movies", movies);
        return "adminMoviesToConfirm";
    }

    @GetMapping("/confirmMovie/{id}")
    public String confirmMovie(@PathVariable Long id) {
        movieService.approveMovie(id);
        return "redirect:/app/admin/moviesToConfirm";
    }

    @GetMapping("/deleteMovie/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/app/admin/moviesToConfirm";
    }

}
