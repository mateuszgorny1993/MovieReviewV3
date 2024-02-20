package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            System.out.println("Zalogowany użytkownik: " + username); // Tymczasowe logowanie
            model.addAttribute("username", username);
        } else {
            System.out.println("Użytkownik nie jest zalogowany."); // Tymczasowe logowanie
        }
        return "dashboard";
    }

}
