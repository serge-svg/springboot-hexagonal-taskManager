package svg.taskmanager.infra.adapters.input.controllers.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import svg.taskmanager.infra.ports.input.UserInputPort;

@Controller
@Tag(name = "Task Manager")
@RequestMapping("/task-manager")
public class UserDeleteController {

    private final UserInputPort userInputPort;

    public UserDeleteController(UserInputPort userInputPort) {
        super();
        this.userInputPort = userInputPort;
    }

    @DeleteMapping("/delete-user")
    public String delete(Model model, @RequestParam String id) {
        userInputPort.deleteById(id);
        model.addAttribute("users", userInputPort.getAll());
        return "listOfUsers";
    }

}
