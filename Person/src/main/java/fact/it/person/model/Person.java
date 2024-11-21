package fact.it.person.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "people")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Person {
    private String id;
    private String name;
    private boolean visitor;
    private String phone;
    private String email;

}
