package svg.taskmanager.application;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.ports.input.UserInputPort;
import svg.taskmanager.infra.ports.output.EntityRepository;

@Component
public class UserUseCase implements UserInputPort{

    EntityRepository entityRepository;
    TaskUseCase taskUseCase;

    public UserUseCase(EntityRepository entityRepository, TaskUseCase taskUseCase) {
        this.entityRepository = entityRepository;
        this.taskUseCase = taskUseCase;
    }

    @Override
    public List<TMUser> getAll() {
        return entityRepository.getAll(TMUser.class);
    }

    @Override
    public TMUser getById(String id) {
        return entityRepository.getById(id, TMUser.class);
    }

    @Override
    public TMUser getByNationalId(String nationalId) {
        return entityRepository.getByNationalId(nationalId, TMUser.class);
    }
    
    @Override
    public List<TMUser> getByName(String name) {
        return entityRepository.getByName(name, TMUser.class);
    }
    
    @Override
    public boolean create(String national_id, String name, String email) {        
        TMUser user = TMUser.builder()
            .id(UUID.randomUUID().toString())
            .national_id(national_id)
            .name(name)
            .email(email)
            .build();
        
        return entityRepository.save(user);
    }

    @Override
    public boolean deleteById(String id) {
        TMUser tmUser = getById(id);    
        if (null != tmUser && !tmUser.getNational_id().isEmpty()) {  
            String national_id = tmUser.getNational_id();
            taskUseCase.getByUserId(national_id).stream()
                       .forEach((task) -> taskUseCase.deleteById(task.getId()));
        }
        return entityRepository.deleteById(id, TMUser.class);
    }
    
}
