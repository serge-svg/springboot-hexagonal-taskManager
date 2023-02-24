package svg.taskmanager.application;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import svg.taskmanager.domain.TMTask;
import svg.taskmanager.infra.adapters.output.PostgresRepository;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TaskUseCaseTest {
    @Mock
    PostgresRepository postgresRepository;
    
    TaskUseCase taskUseCase;
    private TMTask task;
    private final String ID = "001";
    private final String USER_ID = "11122233A";
    private final String TITLE = "Task1";
    private final String DESCRIPTION = "Task1 for user1...";

    @BeforeEach
    void setUp() {
        taskUseCase =  new TaskUseCase(postgresRepository);
        task = TMTask.builder().id(ID)
                               .user_id(USER_ID)
                               .title(TITLE)
                               .description(DESCRIPTION)
                               .build();
    }
    
    @Test
    void testGetAll() {
        doReturn(Collections.singletonList(task)).when(postgresRepository).getAll(TMTask.class);

        List<TMTask> tasks = taskUseCase.getAll();
        assertThat(tasks).as("List of all tasks").asList()
                .extracting("id")
                .contains(ID)
                .doesNotContain("003");
        
        verify(postgresRepository).getAll(TMTask.class);
    }

    @Test
    void testGetById() {
        doReturn(task).when(postgresRepository).getById(ID, TMTask.class);

        task = taskUseCase.getById(ID);
        assertThat(task).as("Task should have an id, user_id, title and description")
                .extracting("id", "user_id", "title", "description")
                .isNotNull();
    }    

    @Test
    void testGetByUserId() {
        doReturn(Collections.singletonList(task)).when(postgresRepository).getByUserId(USER_ID, TMTask.class);

        List<TMTask> tasks = taskUseCase.getByUserId(USER_ID);
        assertThat(tasks).as("List of tasks")
                .extracting("id")
                .contains(ID)
                .doesNotContain("003");
        
        verify(postgresRepository).getByUserId(USER_ID, TMTask.class);
    }

    @Test
    void testDeleteById() {
        doReturn(true).when(postgresRepository).deleteById(ID, TMTask.class);

        taskUseCase.deleteById(ID);

        verify(postgresRepository).deleteById(ID, TMTask.class);
    }

}
