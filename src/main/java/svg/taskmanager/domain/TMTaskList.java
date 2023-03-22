package svg.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
public class TMTaskList {
    private List<TMTask> tasks;

    public TMTaskList() {
        tasks = new ArrayList<>();
    }
}
