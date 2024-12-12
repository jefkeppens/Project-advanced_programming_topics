package fact.it.frontend.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class EventService {

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    private final RestTemplate restTemplate;

    public EventService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Object> getAllEvents() {
        String url = apiGatewayUrl + "/events";
        return restTemplate.getForObject(url, List.class);
    }
}
