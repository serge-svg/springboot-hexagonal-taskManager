package svg.taskmanager.infra.adapters.input.controllers.tasks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import svg.taskmanager.infra.ports.input.TaskInputPort;

@Controller
@RequestMapping("/task-manager")
public class TaskGetController {

	private final TaskInputPort taskInputPort;


	public TaskGetController(TaskInputPort taskInputPort) {
		super();
		this.taskInputPort = taskInputPort;
	}

	@GetMapping("/tasks")
	public String getAllTasks(Model model) {
		model.addAttribute("tasks", taskInputPort.getAll());
		return "listOfTasks";		
	}	

	@GetMapping("/tasks/user-tasks")
	public String getByUserId(Model model, @RequestParam String userId) {
		model.addAttribute("tasks", taskInputPort.getByUserId(userId));
		model.addAttribute("userId", userId);
		return "listOfTasks";		
	}	
	
	@GetMapping("/tasks/add-taskform")
	public String addForm(Model model, String userId) {
		model.addAttribute("userId", userId);
		return "addTaskForm";
	}

	@GetMapping("/tasks/update-taskform")
	public String updateForm(Model model, @RequestParam String id, @RequestParam String userId) {
		model.addAttribute("task", taskInputPort.getById(id));
		model.addAttribute("userId", userId);
		return "updateTaskForm";
	}

}
