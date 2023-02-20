package svg.taskmanager.infra.ports.input;

import java.util.List;

import svg.taskmanager.domain.TMTask;


public interface TaskInputPort {
    
    public List<TMTask> getAll();
    public TMTask getById(String id);
    public List<TMTask> getByUserId(String userId);
    public TMTask create(String user_id, String title, String description);
    public void delete(String id);
    
}
