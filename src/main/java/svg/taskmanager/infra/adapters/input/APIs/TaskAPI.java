package svg.taskmanager.infra.adapters.input.APIs;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import svg.taskmanager.domain.TMTask;
import svg.taskmanager.infra.ports.input.TaskInputPort;


@RestController
@Tag(name = "Tasks")
@RequestMapping(value = "webapi/taskmanager/tasks/")
public class TaskAPI {

    private TaskInputPort taskInputPort;

    public TaskAPI(TaskInputPort taskInputPort) {
        this.taskInputPort = taskInputPort;
    }

    @Operation(summary = "Finds all tasks")
    @GetMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMTask> getAll() {
        return taskInputPort.getAll();
    }

    @Operation(summary = "Finds a taks by id")
    @GetMapping(value = "getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public TMTask getById(@RequestParam String id) {
        return taskInputPort.getById(id);
    }

    @Operation(summary = "Finds all user tasks")
    @GetMapping(value = "getbyuserid", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMTask> getByUserId(@RequestParam String userId) {
        return taskInputPort.getByUserId(userId);
    }

    @Operation(summary = "Creates a user task")
    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestParam String userId, @RequestParam String title, @RequestParam String description) {
        return taskInputPort.create(userId, title, description);
    }

    @Operation(summary = "Updates a user task")
    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean update(@RequestParam String id, @RequestParam String userId, @RequestParam String title, @RequestParam String description) {
        return taskInputPort.update(id, userId, title, description);
    }

    @Operation(summary = "Deletes a user task")
    @DeleteMapping(value = "delete")
    public boolean deleteById(@RequestParam String id) {
        return taskInputPort.deleteById(id);
    }

}
