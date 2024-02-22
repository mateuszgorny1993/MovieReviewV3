package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.model.Director;
import pl.coderslab.service.DirectorService;

@Controller
@RequestMapping("/directors")
public class DirectorController {
    DirectorService directorService;
    @Autowired
    public DirectorController(DirectorService directorService) {
        this.directorService = directorService;
    }
    @GetMapping
    public String listDirectors(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "sortType", defaultValue = "lastName") String sortType,
            Model model) {
        Page<Director> directorPage = directorService.getApprovedDirectors(page, size, sortType);
        model.addAttribute("directorsPage",directorPage);
        model.addAttribute("currentSort", sortType);
        return "directors";
    }
}
