package svg.taskmanager.infra.adapters.input.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getAll(Model model) {
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";		
	}
		
	@GetMapping("/addUserForm")
	public String addForm() {
		return "addUserForm";
	}
	
	@GetMapping("/deleteUser")
	public String delete(Model model, @RequestParam String id) {		
		userInputPort.deleteById(id);
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";	
	}
	
	@PostMapping("/insertUser")
	public String insert(Model model, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
		userInputPort.create(nationalId, name, email);
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";	
	}

	@GetMapping("/updateUserForm")
	public String updateForm(Model model, @RequestParam String id) {	
		model.addAttribute("user", userInputPort.getById(id));
		return "updateUserForm";
	}

	@GetMapping("/updateUser")
	public String updateForm(Model model, @RequestParam String id, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
		userInputPort.update(id, nationalId, name, email);
		model.addAttribute("users", userInputPort.getAll());
		return "listOfUsers";
	}


}
