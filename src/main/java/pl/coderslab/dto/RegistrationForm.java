package pl.coderslab.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationForm {

    @NotBlank(message = "Nazwa użytkownika jest wymagana.")
    private String username;

    @Email(message = "Podaj poprawny adres email.")
    @NotBlank(message = "Email jest wymagany.")
    private String email;

    @Size(min = 8, message = "Hasło musi zawierać co najmniej 8 znaków.")
    @Pattern.List({
            @Pattern(regexp = ".*[A-Z].*", message = "Hasło musi zawierać co najmniej jedną dużą literę."),
            @Pattern(regexp = ".*[0-9].*", message = "Hasło musi zawierać co najmniej jedną cyfrę.")
    })
    private String password;

    @NotBlank(message = "Imię jest wymagane.")
    private String firstname;

    @NotBlank(message = "Nazwisko jest wymagane.")
    private String lastname;

    private boolean notifications;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

}
