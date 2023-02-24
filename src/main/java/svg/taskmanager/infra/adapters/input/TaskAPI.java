package svg.taskmanager.infra.adapters.input;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import svg.taskmanager.domain.TMTask;
import svg.taskmanager.infra.ports.input.TaskInputPort;


@RestController
@RequestMapping(value = "webapi/taskManager/tasks/")
public class TaskAPI {

    private TaskInputPort taskInputPort;

    public TaskAPI(TaskInputPort taskInputPort) {
        this.taskInputPort = taskInputPort;
    }

    @GetMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMTask> getAll() {
        return taskInputPort.getAll();
    }

    @GetMapping(value = "getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public TMTask getById(@RequestParam String id) {
        return taskInputPort.getById(id);
    }

    @GetMapping(value = "getbyuserid", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMTask> getByUserId(@RequestParam String userId) {
        return taskInputPort.getByUserId(userId);
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestParam String userId, @RequestParam String title, @RequestParam String description) {
        return taskInputPort.create(userId, title, description);
    }

    @DeleteMapping(value = "delete")
    public boolean deleteById(@RequestParam String id) {
        return taskInputPort.deleteById(id);
    }
}
