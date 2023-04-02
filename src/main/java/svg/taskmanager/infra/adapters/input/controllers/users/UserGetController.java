package svg.taskmanager.infra.adapters.input.controllers.users;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import svg.taskmanager.infra.ports.input.UserInputPort;


@Controller
@Tag(name = "Task Manager")
@RequestMapping("/task-manager")
public class UserGetController {
	
	private final UserInputPort userInputPort;
		
	public UserGetController(UserInputPort userInputPort) {
		super();
		this.userInputPort = userInputPort;
	}

	@GetMapping({"/users", "/", ""})
	public String getAll(Model model) {
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";		
	}
		
	@GetMapping("/add-userform")
	public String addForm() {
		return "addUserForm";
	}

	@GetMapping("/update-userform")
	public String updateForm(Model model, @RequestParam String id) {	
		model.addAttribute("user", userInputPort.getById(id));
		return "updateUserForm";
	}



}
