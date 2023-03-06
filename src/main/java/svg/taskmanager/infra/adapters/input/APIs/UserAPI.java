package svg.taskmanager.infra.adapters.input.APIs;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.ports.input.UserInputPort;

import org.springframework.http.MediaType;


@RestController
@RequestMapping(value = "/webapi/taskManager/users/")
public class UserAPI {

    private UserInputPort userInputPort;

    public UserAPI(UserInputPort userInputPort) {
        this.userInputPort = userInputPort;
    }

    @GetMapping(value = "getall", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMUser> getAll() {
        return userInputPort.getAll();
    }

    @GetMapping(value = "getbyid", produces = MediaType.APPLICATION_JSON_VALUE)
    public TMUser getById(@RequestParam String id) {
        return userInputPort.getById(id);
    }

    @GetMapping(value = "getbynationalid", produces = MediaType.APPLICATION_JSON_VALUE)
    public TMUser getByNationalId(@RequestParam String nationalId) {
        return userInputPort.getByNationalId(nationalId);
    }

    @GetMapping(value = "getbyname", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TMUser> getByName(@RequestParam String name) {
        return userInputPort.getByName(name);
    }

    @PostMapping(value = "create", produces=MediaType.APPLICATION_JSON_VALUE)
    public boolean create(@RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
        return userInputPort.create(nationalId, name, email);
    }

    
    @PostMapping(value = "update", produces=MediaType.APPLICATION_JSON_VALUE)
    public boolean update(@RequestParam String id, @RequestParam String nationalId, @RequestParam String name, @RequestParam String email) {
        return userInputPort.update(id, nationalId, name, email);
    }
    
    @DeleteMapping(value = "delete")
    public boolean deleteById(@RequestParam String id) {        
        return userInputPort.deleteById(id);
    }
    
}
