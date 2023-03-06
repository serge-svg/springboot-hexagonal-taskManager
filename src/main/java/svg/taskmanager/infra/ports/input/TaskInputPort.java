package svg.taskmanager.infra.ports.input;

import java.util.List;

import svg.taskmanager.domain.TMTask;


public interface TaskInputPort {
    
    public List<TMTask> getAll();
    public TMTask getById(String id);
    public List<TMTask> getByUserId(String userId);
    public boolean create(String user_id, String title, String description);
    public boolean update(String id, String user_id, String title, String description);
    public boolean deleteById(String id);
    
}
