package svg.taskmanager.infra.adapters.input.APIs;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import svg.taskmanager.domain.TMTask;
import svg.taskmanager.domain.TMTaskList;
import svg.taskmanager.infra.ports.output.EntityRepository;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
class TaskAPITest {

        @LocalServerPort
        private int port;
        @MockBean
        private EntityRepository entityRepository;
        @Autowired
        private TestRestTemplate testRestTemplate;

        @Test
        @DisplayName("Should call the get API")
        void getAll() {
                var task = TMTask.builder()
                                .id("001")
                                .title("Increase resources")
                                .description("Increase memory")
                                .userId("1112233A")
                                .build();

                Mockito.when(entityRepository.getAll(any())).thenReturn(List.of(task));

                var url = UriComponentsBuilder.newInstance()
                                .scheme("http")
                                .host("localhost")
                                .port(port)
                                .path("/webapi/taskmanager/tasks")
                                .path("/getall")
                                .toUriString();
                ResponseEntity<List<TMTask>> response = testRestTemplate.exchange(url,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<>() {
                                });

                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                Assertions.assertThat(response.getBody()).contains(task);
        }

        @Test
        @DisplayName("Should calls the getbyid and return a concrete task")
        void getById() {
                var task = TMTask.builder()
                                .id("001")
                                .title("Increase resources")
                                .description("Increase memory")
                                .userId("1112233A")
                                .build();

                Mockito.when(entityRepository.getById("001", TMTask.class)).thenReturn(task);

                var url = UriComponentsBuilder.newInstance()
                                .scheme("http")
                                .host("localhost")
                                .port(port)
                                .path("/webapi/taskmanager/tasks")
                                .path("/getbyid")
                                .queryParam("id", "001")
                                .toUriString();

                ResponseEntity<TMTask> response = testRestTemplate.exchange(url,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<>() {});
                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                Assertions.assertThat(response.getBody()).isEqualTo(task);                
        }


        @Test
        @DisplayName("Should calls the getbyuserid and return a concrete list of tasks")
        void getByUserId() {
                var task1 = TMTask.builder()
                        .id("001")
                        .title("Increase resources")
                        .description("Increase memory")
                        .userId("1112233A")
                        .build();

                var task2 = TMTask.builder()
                        .id("002")
                        .title("Increase resources")
                        .description("Increase memory")
                        .userId("11122233A")
                        .build();
                var tasks = new TMTaskList();
                /* Luiz: How to test than the call is returning 2 tasks ??? is it worth it ???
                List<TMTask> listOfTasks = new ArrayList<>();
                listOfTasks.add(task1);
                listOfTasks.add(task2);
                tasks.setTasks(listOfTasks);
                */
                Mockito.when(entityRepository.getByUserId("11122233A", TMTask.class)).thenReturn((List<TMTask>) any(TMTaskList.class));

                var url = UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("localhost")
                        .port(port)
                        .path("/webapi/taskmanager/tasks")
                        .path("/getbyuserid")
                        .queryParam("userId", "11122233A")
                        .toUriString();

                ResponseEntity<TMTask> response = testRestTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {});

                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                //Assertions.assertThat(response.getBody()).isEqualTo(task);

        }
}