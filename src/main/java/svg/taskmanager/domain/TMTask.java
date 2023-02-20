package svg.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMTask {
    private String id;
    private String user_id;
    private String title;
    private String description;
}
