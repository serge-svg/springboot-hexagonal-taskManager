package svg.taskmanager.infra.adapters.output;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import svg.taskmanager.domain.TMUser;
import svg.taskmanager.domain.TMTask;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PostgresRepositoryTest {
    @Autowired
    PostgresRepository postgresRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    private TMUser user;
    private final String ID = "001";
    private final String NATIONAL_ID = "11122233A";
    private final String NAME = "User1";
    private final String EMAIL = "user1@hotmail";
    
    private TMTask task;
    private final String USER_ID = "11122233A";
    private final String TITLE = "Task1";
    private final String DESCRIPTION = "Task1 for user1...";

    @BeforeEach
    void setUp() {        
        user = TMUser.builder().id(ID)
                               .national_id(NATIONAL_ID)
                               .name(NAME)
                               .email(EMAIL)
                               .build();

        task = TMTask.builder().id(ID)
                               .user_id(USER_ID)
                               .title(TITLE)
                               .description(DESCRIPTION)
                               .build();

        }

    @AfterEach
    void cleanEnvironment(){
        postgresRepository.deleteAll(TMTask.class);
        postgresRepository.deleteAll(TMUser.class);
    }

    @Test
    void should_find_all_created_usesr() {
        postgresRepository.save(user);
        List<TMUser> savedUser = postgresRepository.getAll(TMUser.class);

        assertThat(savedUser.get(0).getId()).isEqualTo(ID);
    }

    @Test
    void should_find_a_created_user() {
        postgresRepository.save(user);
        TMUser savedUser = postgresRepository.getById(ID, TMUser.class);

        assertThat(savedUser.getId()).isEqualTo(ID);
    }

    @Test
    void should_find_a_user_by_its_national_id() {
        postgresRepository.save(user);
        TMUser userFound = postgresRepository.getByNationalId(NATIONAL_ID, TMUser.class);

        assertThat(userFound.getId()).isEqualTo(ID);
    }

    @Test
    void should_find_users_by_name() {
        postgresRepository.save(user);
        List<TMUser> usersFound = postgresRepository.getByName(NAME, TMUser.class);

        assertThat(usersFound.get(0).getId()).isEqualTo(ID);
    }

    @Test
    void should_find_tasks_by_user_id() {
        postgresRepository.save(user);
        postgresRepository.save(task);
        List<TMTask> tasksFound = postgresRepository.getByUserId(USER_ID, TMTask.class);

        assertThat(tasksFound.get(0).getId()).isEqualTo(ID);
    }

    @Test
    void should_not_find_a_deleted_user() {
        postgresRepository.deleteById(ID, TMUser.class);
        TMUser savedUser = postgresRepository.getById(ID, TMUser.class);

        assertThat(savedUser).isNull();
    }

    @Test
    void should_not_find_a_deleted_task() {
        postgresRepository.deleteById(ID, TMTask.class);
        TMTask savedTask = postgresRepository.getById(ID, TMTask.class);

        assertThat(savedTask).isNull();
    }

    @Test
    void should_deleted_all_tasks() {
        postgresRepository.save(user);
        postgresRepository.save(task);
        postgresRepository.deleteAll(TMTask.class);
        TMTask savedTask = postgresRepository.getById(ID, TMTask.class);

        assertThat(savedTask).isNull();
    }
    
    @Test
    void should_deleted_all_users() {
        postgresRepository.save(user);
        postgresRepository.deleteAll(TMUser.class);
        TMUser savedUser = postgresRepository.getById(ID, TMUser.class);

        assertThat(savedUser).isNull();
    }    
}
