package svg.taskmanager.infra.adapters.input.controllers.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import svg.taskmanager.infra.ports.input.UserInputPort;

@Controller
@Tag(name = "Task Manager")
@RequestMapping("/task-manager")
public class UserPostController {

    private final UserInputPort userInputPort;

    public UserPostController(UserInputPort userInputPort) {
        super();
        this.userInputPort = userInputPort;
    }

    @PostMapping("/insert-user")
    @ResponseStatus(HttpStatus.CREATED)
    public String insert(Model model, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
        userInputPort.create(nationalId, name, email);
        model.addAttribute("users", userInputPort.getAll());
        return "listOfUsers";
    }
}
