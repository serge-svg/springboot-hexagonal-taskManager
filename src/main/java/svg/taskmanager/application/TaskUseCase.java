package svg.taskmanager.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import svg.taskmanager.domain.TMTask;
import svg.taskmanager.infra.ports.input.TaskInputPort;
import svg.taskmanager.infra.ports.output.EntityRepository;


@Component
public class TaskUseCase implements TaskInputPort{
    
    EntityRepository entityRepository;

    public TaskUseCase(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Override
    public List<TMTask> getAll() {
        return entityRepository.getAll(TMTask.class);
    }
    
    @Override
    public TMTask getById(String id) {
        return entityRepository.getById(id, TMTask.class);
    }
    
    @Override
    public List<TMTask> getByUserId(String userId) {
        return entityRepository.getByUserId(userId, TMTask.class);
    }

    @Override
    public boolean create(String userId, String title, String description) {        
        TMTask task = TMTask.builder()
            .id(UUID.randomUUID().toString())
            .userId(userId)
            .title(title)
            .description(description)
            .build();
        
        return entityRepository.save(task);
    }

    @Override
    public boolean update(String id, String userId, String title, String description) {        
        TMTask task = TMTask.builder()
            .id(id)
            .userId(userId)
            .title(title)
            .description(description)
            .build();
        
        return entityRepository.update(task);
    }

    @Override
    public boolean deleteById(String id) {
        return entityRepository.deleteById(id, TMTask.class);
    }

}
