package svg.taskmanager.infra.adapters.input.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import svg.taskmanager.application.UserUseCase;
import svg.taskmanager.domain.TMUser;

@Controller
public class HomeController {

    private final UserUseCase userUseCase;

    @Autowired
    public HomeController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        model.addAttribute("users", userUseCase.getAll());
        model.addAttribute("newPlayer", new TMUser());
        return "home";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute TMUser tmUser) {
        userUseCase.create(tmUser.getNationalId(), tmUser.getName(), tmUser.getEmail());
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        userUseCase.deleteById(id);
        return "redirect:/home";
    }