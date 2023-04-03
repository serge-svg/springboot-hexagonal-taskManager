package svg.taskmanager.infra.adapters.input.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import svg.taskmanager.infra.adapters.input.controllers.users.UserPutController;
import svg.taskmanager.infra.ports.input.UserInputPort;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = UserPutController.class)
@RunWith(MockitoJUnitRunner.class)
class UserPutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInputPort userInputPort;
/*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
*/
    @Test
    @DisplayName("Should call the update controller method to update a user")
    void shouldHandlePostCall() throws Exception {
        var id = "001";
        var nationalId = "11122233A";
        var name = "user1";
        var email = "user1@mail.net";

        when(userInputPort.update(id, nationalId, name, email)).thenReturn(true);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/task-manager/update-user")
                        .param("id", id)
                        .param("nationalId", nationalId)
                        .param("name", name)
                        .param("email", email))
                .andDo(print()).andExpect(status().isAccepted())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("listOfUsers"));
    }

}