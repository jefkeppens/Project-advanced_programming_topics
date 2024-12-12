package fact.it.frontend.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import java.util.List;

@Service
public class EventService {

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;
    private final AuthService authService;

    private final RestTemplate restTemplate;

    public EventService(AuthService authService) {
        this.restTemplate = new RestTemplate();
        this.authService = authService;
    }

    public List<Object> getAllEvents() {
        String url = apiGatewayUrl + "/events";
        return restTemplate.getForObject(url, List.class);
    }

    public Object addEvent(Object eventRequest) {
        String url = apiGatewayUrl + "/events/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());

        HttpEntity<Object> requestEntity = new HttpEntity<>(eventRequest, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, Object.class
        );

        return response.getBody();
    }

    public Object editEvent(String eventName) {
        String url = apiGatewayUrl + "/events/update/" + eventName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url, HttpMethod.DELETE, requestEntity, Object.class
        );

        return response.getBody();
    }


    public Object deleteEvent(String eventName) {
        String url = apiGatewayUrl + "/events/delete/" + eventName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url, HttpMethod.DELETE, requestEntity, Object.class
        );

        return response.getBody();
    }
}
