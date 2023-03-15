package svg.taskmanager.infra.adapters.input.APIs;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.ports.input.UserInputPort;

import org.springframework.http.MediaType;


@RestController
@Tag(name = "Users")
@RequestMapping(value = "/webapi/taskmanager/users/")
public class UserAPI {

    private UserInputPort userInputPort;

    public UserAPI(UserInputPort userInputPort) {
        this.userInputPort = userInputPort;
    }

    @Operation(summary = "Find all users")
    @GetMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMUser> getAll() {
        return userInputPort.getAll();
    }

    @Operation(summary = "Find user by id")
    @GetMapping(value = "getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public TMUser getById(@RequestParam String id) {
        return userInputPort.getById(id);
    }

    @Operation(summary = "Find user by national id")
    @GetMapping(value = "getbynationalid", produces = MediaType.APPLICATION_JSON_VALUE)
    public TMUser getByNationalId(@RequestParam String nationalId) {
        return userInputPort.getByNationalId(nationalId);
    }

    @Operation(summary = "Find users by name")
    @GetMapping(value = "getbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMUser> getByName(@RequestParam String name) {
        return userInputPort.getByName(name);
    }

    @Operation(summary = "Create a new user")
    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
        return userInputPort.create(nationalId, name, email);
    }

    @Operation(summary = "Update an existing user")
    @PutMapping(value = "update", produces=MediaType.APPLICATION_JSON_VALUE)
    public boolean update(@RequestParam String id, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
        return userInputPort.update(id, nationalId, name, email);
    }
    
    @Operation(summary = "Delete by id an existing user")
    @DeleteMapping(value = "delete")
    public boolean deleteById(@RequestParam String id) {        
        return userInputPort.deleteById(id);
    }
    
}
