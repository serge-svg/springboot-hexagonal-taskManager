package svg.taskmanager.infra.adapters.input.controllers.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import svg.taskmanager.infra.ports.input.TaskInputPort;

@Controller
@RequestMapping("/task-manager")
public class TaskPostController {

    private final TaskInputPort taskInputPort;

    public TaskPostController(TaskInputPort taskInputPort) {
        super();
        this.taskInputPort = taskInputPort;
    }
    @PostMapping("/tasks/insert-task")
    @ResponseStatus(HttpStatus.CREATED)
    public String insert(Model model, @RequestParam String userId, @RequestParam String title, @RequestParam String description) {
        taskInputPort.create(userId, title, description);
        model.addAttribute("userId", userId);
        model.addAttribute("tasks", taskInputPort.getByUserId(userId));
        return "listOfTasks";
    }

}
