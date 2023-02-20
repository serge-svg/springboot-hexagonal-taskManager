package svg.taskmanager.infra.ports.output;

import java.util.List;

public interface EntityRepository {
    public <T> List<T> getAll(Class<T> clazz);
    public <T> T getById(String id, Class<T> clazz);   
    public <T> T getByNationalId(String nationalId, Class<T> clazz);
    public <T> List<T> getByUserId(String userId, Class<T> clazz);
    public <T> List<T> getByName(String id, Class<T> clazz);      
    public <T> T save(T reg);
    public <T> boolean delete(String id, Class<T> clazz);
}
