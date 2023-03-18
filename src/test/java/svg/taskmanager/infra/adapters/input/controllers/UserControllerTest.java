package svg.taskmanager.infra.adapters.input.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import svg.taskmanager.application.UserUseCase;
import svg.taskmanager.domain.TMUser;
import svg.taskmanager.infra.ports.input.UserInputPort;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ContextConfiguration(classes = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserUseCase userUseCase;

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
    @DisplayName("Should call the get API")
    void shouldHandleGetCall(String segment) throws Exception {
        when(userUseCase.getAll()).thenReturn(List.of(TMUser.builder().build()));

        this.mockMvc.perform(get("/task-manager/" + segment))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("listOfUsers"))
                .andExpect(model().attributeExists("users"));

    }

    @Test
    @DisplayName("Should call the get API to add User Form")
    void shouldHandlePostCall() throws Exception {
        this.mockMvc.perform(get("/task-manager/addUserForm"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("addUserForm"));
    }

    @Test
    @DisplayName("Should call the get API to delete a User")
    void shouldHandleDeleteMethod() throws Exception {
        var id = "42";
        when(userInputPort.deleteById(id)).thenReturn(true);

        this.mockMvc.perform(get("/task-manager/deleteUser")
                        .param("id", id))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("listOfUsers"))
                .andExpect(model().attributeExists("users"));
    }
}