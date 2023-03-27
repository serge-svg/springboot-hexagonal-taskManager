package svg.taskmanager.infra.adapters.input.APIs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.ports.input.UserInputPort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class UserAPITest {
    private MockMvc mockMvc;
    @Mock
    private UserInputPort userInputPort;
    @InjectMocks
    private UserAPI userAPI;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userAPI)
                .build();
    }

    @Test
    @DisplayName("Should call the getall userAPI method and return status code 200")
    void getAll() throws Exception {
        List<TMUser> users = new ArrayList<>();
        users.add(TMUser.builder()
                .id("001")
                .nationalId("ENG")
                .name("Luiz")
                .email("luiz@codemaster.com")
                .build());

        Mockito.when(userAPI.getAll()).thenReturn(users);

        MockHttpServletResponse response = mockMvc.perform(get("/webapi/task-manager/users/getall")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("luiz@codemaster.com");
    }

    @Test
    @DisplayName("Should call the getById userAPI method and return a status 200")
    void getById() throws Exception {
        TMUser user = TMUser.builder()
                .id("001")
                .nationalId("ENG")
                .name("John")
                .email("email@email.co")
                .build();

        Mockito.when(userAPI.getById("001")).thenReturn(user);

        MockHttpServletResponse response = mockMvc.perform(get("/webapi/task-manager/users/getbyid?id=001")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should call the getByNationalId userAPI method and return a status 200")
    void getByNationalId() throws Exception {
        TMUser user = TMUser.builder()
                .id("001")
                .nationalId("11122233A")
                .name("John")
                .email("email@email.co")
                .build();

        Mockito.when(userAPI.getByNationalId("11122233A")).thenReturn(user);

        MockHttpServletResponse response = mockMvc.perform(get("/webapi/task-manager/users/getbynationalid?nationalId=11122233A")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should call the getbyname userAPI method and return a status 200")
    void getbyname() throws Exception {
        List<TMUser> users = new ArrayList<>();
        users.add(TMUser.builder()
                .id("001")
                .nationalId("ENG")
                .name("Luiz")
                .email("luiz@codemaster.com")
                .build());

        Mockito.when(userAPI.getByName("Luiz")).thenReturn(users);

        MockHttpServletResponse response = mockMvc.perform(get("/webapi/task-manager/users/getbyname?name=Luiz")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should call the create userAPI method and return a status 200")
    void create() throws Exception {

        Mockito.when(userAPI.create("11122233A", "user1", "user1@codemaster.com")).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/webapi/task-manager/users/create?nationalId=11122233A&name=user1&email=user1@codemaster.com"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should call the update userAPI method and return a status 200")
    void update() throws Exception {

        Mockito.when(userAPI.update("001","11122233A", "user1", "user1@codemaster.com")).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/webapi/task-manager/users/update?id=001&nationalId=11122233A&name=user1&email=user1@codemaster.com"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Should call the delete userAPI method and return a status 200")
    void delete() throws Exception {

        Mockito.when(userAPI.deleteById("001")).thenReturn(true);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/webapi/task-manager/users/delete?id=001"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}