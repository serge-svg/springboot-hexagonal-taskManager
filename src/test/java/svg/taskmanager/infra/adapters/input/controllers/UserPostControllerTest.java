package svg.taskmanager.infra.adapters.input.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import svg.taskmanager.application.UserUseCase;
import svg.taskmanager.infra.adapters.input.controllers.users.UserPostController;
import svg.taskmanager.infra.ports.input.UserInputPort;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = UserPostController.class)
class UserPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInputPort userInputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should call the insert controller method to add a new user")
    void shouldHandlePostCall() throws Exception {
        var nationalId = "11122233A";
        var name = "user1";
        var email = "user1@mail.net";

        when(userInputPort.create(nationalId, name, email)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/task-manager/insert-user")
                        .param("nationalId", nationalId)
                        .param("name", name)
                        .param("email", email))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("listOfUsers"));
    }

}