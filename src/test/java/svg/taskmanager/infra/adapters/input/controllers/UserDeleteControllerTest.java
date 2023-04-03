package svg.taskmanager.infra.adapters.input.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import svg.taskmanager.infra.adapters.input.controllers.users.UserDeleteController;
import svg.taskmanager.infra.ports.input.UserInputPort;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = UserDeleteController.class)
class UserDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInputPort userInputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should call the delete controller method to delete a user")
    void shouldHandleDeleteCall() throws Exception {
        var id = "42";
        when(userInputPort.deleteById(id)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/task-manager/delete-user")
                        .param("id", id))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("listOfUsers"));
    }
}