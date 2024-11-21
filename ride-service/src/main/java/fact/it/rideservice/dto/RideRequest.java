package fact.it.rideservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RideRequest {
    private String name;
    private String type;
    private int capacity;
    private int duration;
    private String responsibleEmail;
}
