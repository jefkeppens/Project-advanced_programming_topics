package fact.it.person.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonResponse {
    private String id;
    private String name;
    private String email;
    private String phone;
    private boolean visitor;
}
