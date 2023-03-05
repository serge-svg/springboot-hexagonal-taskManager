package svg.taskmanager.infra.adapters.input.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import svg.taskmanager.infra.ports.input.TaskInputPort;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	private TaskInputPort taskInputPort;


	public TaskController(TaskInputPort taskInputPort) {
		super();
		this.taskInputPort = taskInputPort;
	}

	@GetMapping("/listOfTasks")
	public String getAllTasks(Model model) {
		model.addAttribute("tasks", taskInputPort.getAll());
		return "listOfTasks";		
	}	

	@GetMapping("/listOfTasksByUser")
	public String getByUserId(Model model, @RequestParam String userId) {
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		model.addAttribute("userId", userId);
		return "listOfTasks";		
	}	
	
	@GetMapping("/addTaskForm")
	public String addForm(Model model, String userId) {
		model.addAttribute("userId", userId);
		return "addTaskForm";
	}
	
	@PostMapping("/insertTask")
	public String insert(Model model, @RequestParam String userId, @RequestParam String title, @RequestParam String description) {
		taskInputPort.create(userId, title, description);
		model.addAttribute("userId", userId);
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		return "listOfTasks";	
	}

	@GetMapping("/deleteTask")
	public String deleteById(Model model, @RequestParam String id, @RequestParam String userId) {
		taskInputPort.deleteById(id);
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		return "listOfTasks";	
	}	

	/*
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
	*/

}
