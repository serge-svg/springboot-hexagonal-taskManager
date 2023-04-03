package svg.taskmanager.infra.adapters.input.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.adapters.input.controllers.users.UserGetController;
import svg.taskmanager.infra.ports.input.UserInputPort;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = UserGetController.class)
class UserGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInputPort userInputPort;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "''",
            "/",
            "/users"
    })
    @DisplayName("Should call the getAll controller method to return all the users")
    void shouldHandleGetAllCall(String segment) throws Exception {
        when(userInputPort.getAll()).thenReturn(List.of(TMUser.builder().build()));

        this.mockMvc.perform(get("/task-manager/" + segment))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("listOfUsers"))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    @DisplayName("Should call the addForm controller method to show the addUserForm")
    void shouldHandleGetAddFormCall() throws Exception {
        this.mockMvc.perform(get("/task-manager/add-userform"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("addUserForm"));
    }

    @Test
    @DisplayName("Should call the updateForm controller method to show the updateUserForm")
    void shouldHandleGetUpdateFormCall() throws Exception {
        var id = "01";
        when(userInputPort.getById(id)).thenReturn(TMUser.builder().build());

        this.mockMvc.perform(get("/task-manager/update-userform")
                    .param("id", id))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("updateUserForm"))
                .andExpect(model().attributeExists("user"));;
    }

}