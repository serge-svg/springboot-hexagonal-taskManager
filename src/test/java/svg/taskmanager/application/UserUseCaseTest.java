package svg.taskmanager.application;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.adapters.output.PostgresRepository;
import svg.taskmanager.infra.ports.output.EntityRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {
    @Mock
    EntityRepository entityRepository;
    @Mock
    PostgresRepository postgresRepository;
    @Mock
    TaskUseCase taskUseCase;

    private UserUseCase userUseCase;
    private TMUser user;
    private final String ID = "001";
    private final String NATIONAL_ID = "11122233A";
    private final String NAME = "User1";
    private final String EMAIL = "user1@hotmail";

    @BeforeEach
    void setUp() {
        userUseCase =  new UserUseCase(postgresRepository, taskUseCase);
        user = TMUser.builder().id(ID)
                               .nationalId(NATIONAL_ID)
                               .name(NAME)
                               .email(EMAIL).build();
    }

    @Test
    void getAll() {
        doReturn(Collections.singletonList(user)).when(postgresRepository).getAll(TMUser.class);
        
        var users = userUseCase.getAll();
        assertThat(users).as("List of all users").asList()
                .extracting("id")
                .contains(ID)
                .doesNotContain("003");

        verify(postgresRepository).getAll(TMUser.class);
    }

    @Test
    void getById() {
        doReturn(user).when(postgresRepository).getById(ID, TMUser.class);

        user = userUseCase.getById(ID);
        assertThat(user).as("User should have an id, national_id, name and email")
                .extracting("id", "national_id", "name", "email")
                .isNotNull();
    }
    
    @Test
    void getByNationalId() {
        doReturn(user).when(postgresRepository).getByNationalId(NATIONAL_ID, TMUser.class);

        user =  userUseCase.getByNationalId(NATIONAL_ID);
        assertThat(user).as("User should have an id, national_id, name and email")
        .extracting("id", "national_id", "name", "email")
        .isNotNull();
    }

    @Test
    void getByName() {
        doReturn(Collections.singletonList(user)).when(postgresRepository).getByName(NAME, TMUser.class);

        var users =  userUseCase.getByName(NAME);
        assertThat(users).as("List of users").asList()
                .extracting("id")
                .contains(ID)
                .doesNotContain("003");

        verify(postgresRepository).getByName(NAME, TMUser.class);
    }

    @Test
    void deleteById() {
        doReturn(true).when(postgresRepository).deleteById(ID, TMUser.class);

        userUseCase.deleteById(ID);

        verify(postgresRepository).deleteById(ID, TMUser.class);
    }

    @Test
    void create() {
        doReturn(true).when(postgresRepository).save(any());

        userUseCase.create(NATIONAL_ID, NAME, EMAIL);
        ArgumentCaptor<TMUser> userCaptor = ArgumentCaptor.forClass(TMUser.class);     
        verify(postgresRepository).save(userCaptor.capture());

        assertEquals(userCaptor.getValue().getNationalId(), NATIONAL_ID);
        assertEquals(userCaptor.getValue().getName(), NAME);
        assertEquals(userCaptor.getValue().getEmail(), EMAIL);
    }

}
