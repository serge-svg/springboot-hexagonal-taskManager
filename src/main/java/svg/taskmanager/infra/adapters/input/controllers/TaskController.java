package svg.taskmanager.infra.adapters.input.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tasks")
public class TaskController {
/*
	private UserService userService;

	public TaskController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/listOfTasksByUser")
	public String findByUser(Model model, @RequestParam String id) {
		model.addAttribute("tasks", userService.findAllTasksByUser(id));
		model.addAttribute("id", id);
		return "listOfTasks";		
	}	
	
	@GetMapping("/addTask")
	public String addForm(Model model, @RequestParam String userID) {
		Task task = new Task();
		Optional<User> oUser = userService.findById(userID);
		if (oUser.isPresent()) {
			task.setUser(oUser.get());
		}
		model.addAttribute("task", task);
		return "addTaskForm";
	}
	
	@PostMapping("/insertTask")
	public String insert(Model model, Task task) {
		userService.insert(task);
		model.addAttribute("tasks", userService.findAllTasksByUser(task.getUser().getId()));
		return "listOfTasks";	
	}
	
	@GetMapping("/updateTaskForm")
	public String updateForm(Model model, @RequestParam int id, @RequestParam String userID) {
		model.addAttribute("task", userService.findById(id));
		model.addAttribute("userID", userID);
		return "updateTaskForm";
	}
	
	@GetMapping("/updateTask")
	public String update(Model model, Task task) {
		userService.update(task);
		model.addAttribute("tasks", userService.findAllTasksByUser(task.getUser().getId()));
		return "listOfTasks";	
	}
	
	@GetMapping("/deleteTask")
	public String delete(Model model, @RequestParam int id, @RequestParam String userID) {
		userService.delete(new Task(id));
		model.addAttribute("tasks", userService.findAllTasksByUser(userID));
		return "listOfTasks";	
	}	
	*/
}
