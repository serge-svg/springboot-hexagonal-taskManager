package svg.taskmanager.infra.adapters.input.controllers.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import svg.taskmanager.infra.ports.input.UserInputPort;

@Controller
@Tag(name = "Task Manager")
@RequestMapping("/task-manager")
public class UserPutController {
    private final UserInputPort userInputPort;

    public UserPutController(UserInputPort userInputPort) {
        super();
        this.userInputPort = userInputPort;
    }

    @PutMapping("/update-user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String updateForm(Model model, @RequestParam String id, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
        userInputPort.update(id, nationalId, name, email);
        model.addAttribute("users", userInputPort.getAll());
        return "listOfUsers";
    }
}
