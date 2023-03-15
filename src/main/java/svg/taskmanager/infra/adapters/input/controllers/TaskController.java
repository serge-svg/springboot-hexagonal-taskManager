package svg.taskmanager.infra.adapters.input.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	@GetMapping("/tasks")
	public String getAllTasks(Model model) {
		model.addAttribute("tasks", taskInputPort.getAll());
		return "listOfTasks";		
	}	

	@GetMapping("/user-tasks")
	public String getByUserId(Model model, @RequestParam String userId) {
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		model.addAttribute("userId", userId);
		return "listOfTasks";		
	}	
	
	@GetMapping("/add-taskform")
	public String addForm(Model model, String userId) {
		model.addAttribute("userId", userId);
		return "addTaskForm";
	}
	
	@PostMapping("/insert-task")
	public String insert(Model model, @RequestParam String userId, @RequestParam String title, @RequestParam String description) {
		taskInputPort.create(userId, title, description);
		model.addAttribute("userId", userId);
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		return "listOfTasks";	
	}

	@DeleteMapping("/delete-task")
	public String deleteById(Model model, @RequestParam String id, @RequestParam String userId) {
		taskInputPort.deleteById(id);
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		return "listOfTasks";	
	}	

	@GetMapping("/update-taskform")
	public String updateForm(Model model, @RequestParam String id, @RequestParam String userId) {
		model.addAttribute("task", taskInputPort.getById(id));
		model.addAttribute("userId", userId);
		return "updateTaskForm";
	}
	
	@PostMapping("/update-task")
	public String update(Model model, @RequestParam String id, @RequestParam String userId, @RequestParam String title, @RequestParam String description) {
		taskInputPort.update(id, userId, title, description);
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		return "listOfTasks";	
	}	

}
