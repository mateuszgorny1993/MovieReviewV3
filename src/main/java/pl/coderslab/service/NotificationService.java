package pl.coderslab.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final EmailService emailService;
    private final UserService userService;

    public NotificationService(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    public void notifyModeratorsAboutNewMovie(String movieTitle) {
        List<String> moderatorEmails = userService.findEmailsByModeratorRole();
        for (String email : moderatorEmails) {
            emailService.sendWelcomeEmail(email, "Nowy film do zatwierdzenia", "Film " + movieTitle + " czeka na Twoje zatwierdzenie.");
        }
    }
}
