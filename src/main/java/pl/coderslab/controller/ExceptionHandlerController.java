package pl.coderslab.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.coderslab.exception.UserAlreadyExistsException;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String handleUserAlreadyExistsException(UserAlreadyExistsException e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        return "register";
    }
}
