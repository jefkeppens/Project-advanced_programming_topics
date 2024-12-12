package fact.it.frontend.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class RideService {

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    private final RestTemplate restTemplate;

    public RideService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Object> getAllRides() {
        String url = apiGatewayUrl + "/rides";
        return restTemplate.getForObject(url, List.class);
    }
}