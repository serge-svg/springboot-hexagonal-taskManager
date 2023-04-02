package svg.taskmanager.infra.adapters.input.controllers.tasks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import svg.taskmanager.infra.ports.input.TaskInputPort;

@Controller
@RequestMapping("/task-manager")
public class TaskDeleteController {

    private final TaskInputPort taskInputPort;

    public TaskDeleteController(TaskInputPort taskInputPort) {
        super();
        this.taskInputPort = taskInputPort;
    }
    @DeleteMapping("/tasks/delete-task")
    public String deleteById(Model model, @RequestParam String id, @RequestParam String userId) {
        taskInputPort.deleteById(id);
        model.addAttribute("tasks", taskInputPort.getByUserId(userId));
        return "listOfTasks";
    }

}
