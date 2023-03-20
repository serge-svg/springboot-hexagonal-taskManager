package svg.taskmanager.infra.adapters.input.APIs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.ports.input.UserInputPort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
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
    @DisplayName("Get all users")
    void getAll() throws Exception {
        List<TMUser> users = new ArrayList<>();
        users.add(TMUser.builder()
                .nationalId("ENG")
                .name("John")
                .email("email@email.co")
                .build());
        Mockito.when(userAPI.getAll()).thenReturn(users);

        MockHttpServletResponse response = mockMvc.perform(get("/webapi/taskmanager/users/getall")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("email@email.co");

    }

}