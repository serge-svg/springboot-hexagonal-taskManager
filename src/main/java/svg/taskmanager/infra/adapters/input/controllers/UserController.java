package svg.taskmanager.infra.adapters.input.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import svg.taskmanager.infra.ports.input.UserInputPort;


@Controller
@Tag(name = "Task Manager")
@RequestMapping("/taskmanager")
public class UserController {
	
	private UserInputPort userInputPort;
		
	public UserController(UserInputPort userInputPort) {
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
	
	@PostMapping("/insert-user")
	@ResponseStatus(HttpStatus.CREATED)
	public String insert(Model model, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
		userInputPort.create(nationalId, name, email);
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";	
	}

	@GetMapping("/update-userform")
	public String updateForm(Model model, @RequestParam String id) {	
		model.addAttribute("user", userInputPort.getById(id));
		return "updateUserForm";
	}

	@PostMapping("/update-user")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public String updateForm(Model model, @RequestParam String id, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
		userInputPort.update(id, nationalId, name, email);
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";
	}

	@GetMapping("/delete-user")
	public String delete(Model model, @RequestParam String id) {		
		userInputPort.deleteById(id);
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";	
	}

}
