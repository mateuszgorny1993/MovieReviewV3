package pl.coderslab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.coderslab.dto.RegistrationForm;
import pl.coderslab.exception.UserAlreadyExistsException;
import pl.coderslab.model.User;
import pl.coderslab.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void registerNewUserAccount(RegistrationForm registrationForm) throws UserAlreadyExistsException {
        if (emailExist(registrationForm.getEmail())) {
            throw new UserAlreadyExistsException("Istnieje już konto z adresem email: " + registrationForm.getEmail());
        }
        if (usernameExist(registrationForm.getUsername())) {
            throw new UserAlreadyExistsException("Istnieje już konto z nazwą użytkownika: " + registrationForm.getUsername());
        }

        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setEmail(registrationForm.getEmail());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        user.setFirstname(registrationForm.getFirstname());
        user.setLastname(registrationForm.getLastname());
        user.setNotifications(registrationForm.isNotifications());

        userRepository.save(user);
        emailService.sendWelcomeEmail(user.getEmail(),
                "Witamy na pokładzie!",
                "Cześć " + user.getFirstname() + ", dziękujemy za rejestrację!");
    }

    private boolean emailExist(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean usernameExist(String username) {
        return userRepository.existsByUsername(username);
    }

}
