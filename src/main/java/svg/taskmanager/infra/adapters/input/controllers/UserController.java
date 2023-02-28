package svg.taskmanager.infra.adapters.input.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import svg.taskmanager.infra.ports.input.UserInputPort;


@Controller
@RequestMapping("/task-manager")
public class UserController {
	
	private UserInputPort userInputPort;
		
	public UserController(UserInputPort userInputPort) {
		super();
		this.userInputPort = userInputPort;
	}

	@GetMapping({"/users", "/", ""})
	public String findAll(Model model) {
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";		
	}
}
