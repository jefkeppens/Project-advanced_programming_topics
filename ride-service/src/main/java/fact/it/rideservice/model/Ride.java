package fact.it.rideservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "ride")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ride {
    private String id;
    private String name;
    private String type;
    private int capacity;
    private int duration;
}
