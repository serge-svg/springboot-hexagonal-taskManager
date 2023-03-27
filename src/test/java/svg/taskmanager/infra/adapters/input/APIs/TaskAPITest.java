package svg.taskmanager.infra.adapters.input.APIs;


import org.apache.commons.lang3.RandomStringUtils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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
import svg.taskmanager.infra.ports.output.EntityRepository;

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

        @InjectMocks
        private TaskAPI taskAPI;

        @Test
        @DisplayName("Should call the getall taskAPI method and return a list of task")
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
                                .path("/webapi/task-manager/tasks")
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
        @DisplayName("Should call the getbyid taskAPI method and return a concrete task")
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
                                .path("/webapi/task-manager/tasks")
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
        @DisplayName("Should call the getbyuserid taskAPI method and return a concrete list of tasks")
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

                Mockito.when(entityRepository.getByUserId("11122233A", TMTask.class)).thenReturn(List.of(task1, task2));

                var url = UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("localhost")
                        .port(port)
                        .path("/webapi/task-manager/tasks")
                        .path("/getbyuserid")
                        .queryParam("userId", "11122233A")
                        .toUriString();

                ResponseEntity<List<TMTask>> response = testRestTemplate.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {
                        });

                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                Assertions.assertThat(response.getBody()).contains(task1, task2);
        }

        @Test
        @DisplayName("Should call the create method taskAPI and return true")
        void create() {
                Mockito.when(entityRepository.save(TMTask.class)).thenReturn(true);

                var url = UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("localhost")
                        .port(port)
                        .path("/webapi/task-manager/tasks")
                        .path("/create")
                        .queryParam("userId", "11122233A")
                        .queryParam("title", "Increase resources")
                        .queryParam("description", "Increase memory")
                        .toUriString();

                ResponseEntity<Object> response = testRestTemplate.exchange(url,
                        HttpMethod.POST,
                        null,
                        new ParameterizedTypeReference<>() {
                        });

                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        @DisplayName("Should call the update taskAPI method and return true")
        void update() {
                Mockito.when(entityRepository.update(TMTask.class)).thenReturn(true);

                var url = UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("localhost")
                        .port(port)
                        .path("/webapi/task-manager/tasks")
                        .path("/update")
                        .queryParam("id", "001")
                        .queryParam("userId", "11122233A")
                        .queryParam("title", "Increase resources")
                        .queryParam("description", "Increase memory updated")
                        .toUriString();

                ResponseEntity<Object> response = testRestTemplate.exchange(url,
                        HttpMethod.PUT,
                        null,
                        new ParameterizedTypeReference<>() {
                        });

                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        @DisplayName("Should call the delete taskAPI method and return true")
        void delete() {
                Mockito.when(entityRepository.save(TMTask.class)).thenReturn(true);

                var url = UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("localhost")
                        .port(port)
                        .path("/webapi/task-manager/tasks")
                        .path("/delete")
                        .queryParam("id", "001")
                        .toUriString();

                ResponseEntity<Object> response = testRestTemplate.exchange(url,
                        HttpMethod.DELETE,
                        null,
                        new ParameterizedTypeReference<>() {
                        });

                Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }


}