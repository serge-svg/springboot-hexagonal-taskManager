package svg.taskmanager.infra.adapters.input.controllers.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import svg.taskmanager.infra.ports.input.TaskInputPort;

@Controller
@RequestMapping("/task-manager")
public class TaskPutController {
    private final TaskInputPort taskInputPort;


    public TaskPutController(TaskInputPort taskInputPort) {
        super();
        this.taskInputPort = taskInputPort;
    }

    @PutMapping("/tasks/update-task")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String update(Model model, @RequestParam String id, @RequestParam String userId, @RequestParam String title, @RequestParam String description) {
        taskInputPort.update(id, userId, title, description);
        model.addAttribute("tasks", taskInputPort.getByUserId(userId));
        return "listOfTasks";
    }

}