package svg.taskmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TMUser {
    private String id;
    private String nationalId;
    private String name;
    private String email;
}
