package svg.taskmanager.infra.ports.input;

import java.util.List;

import svg.taskmanager.domain.TMUser;

public interface UserInputPort {

    public List<TMUser> getAll();
    public TMUser getById(String id);
    public TMUser getByNationalId(String nationalId);
    public List<TMUser> getByName(String name);
    public boolean create(String nationalId, String name, String email);
    public boolean deleteById(String id);
    
}
