package svg.taskmanager.infra.adapters.output;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import svg.taskmanager.domain.TMUser;
import svg.taskmanager.domain.TMTask;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class PostgresRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PostgresRepository postgresRepository;

    @Container
    static final PostgreSQLContainer<?> postgreSQL = (PostgreSQLContainer<?>) new PostgreSQLContainer(
            "postgres:9.6.12")
            .withDatabaseName("taskManagerdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432)
            .waitingFor(Wait.forListeningPort());

    @DynamicPropertySource
    static void postgreSQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQL::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQL::getUsername);
        registry.add("spring.datasource.password", postgreSQL::getPassword);
    }

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
        postgresRepository.deleteAll(TMTask.class);
        postgresRepository.deleteAll(TMUser.class);

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

    @Test
    void should_created_one_user() {
        postgresRepository.save(user);

        var savedUsers = postgresRepository.getAll(TMUser.class);

        assertThat(savedUsers.size()).isEqualTo(1);
    }

    @Test
    void should_created_one_user_and_one_task() {
        postgresRepository.save(user);
        postgresRepository.save(task);

        var savedTasks = postgresRepository.getAll(TMUser.class);

        assertThat(savedTasks.size()).isEqualTo(1);
    }

    @Test
    void should_be_a_unique_national_id_per_user() {
        postgresRepository.save(user);

        TMUser user2 = TMUser.builder().id("002")
                               .national_id(NATIONAL_ID)
                               .name("User2")
                               .email("user2@hotmail")
                               .build();        

        assertThrows(DuplicateKeyException.class, () -> postgresRepository.save(user2));
    }

    @Test
    void should_be_a_unique_email_per_user() {
        postgresRepository.save(user);

        TMUser user2 = TMUser.builder().id("002")
                               .national_id("11122233B")
                               .name("User2")
                               .email(EMAIL)
                               .build();        

        assertThrows(DuplicateKeyException.class, () -> postgresRepository.save(user2));
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
