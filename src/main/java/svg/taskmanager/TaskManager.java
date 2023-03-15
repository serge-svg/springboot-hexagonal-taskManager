package svg.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Task Manager API", version = "1.0", description = "Task Manager API Information"))
public class TaskManager {

	public static void main(String[] args) {
		SpringApplication.run(TaskManager.class, args);
	}

}
