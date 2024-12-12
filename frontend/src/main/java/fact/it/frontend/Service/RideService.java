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
public class RideService {

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;
    private final AuthService authService;

    private final RestTemplate restTemplate;

    public RideService(AuthService authService) {
        this.restTemplate = new RestTemplate();
        this.authService = authService;
    }

    public List<Object> getAllRides() {
        String url = apiGatewayUrl + "/rides";
        return restTemplate.getForObject(url, List.class);
    }

    public Object addRide(Object eventRequest) {
        String url = apiGatewayUrl + "/rides/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());

        HttpEntity<Object> requestEntity = new HttpEntity<>(eventRequest, headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, Object.class
        );

        return response.getBody();
    }


    public Object deleteRide(String rideName) {
        String url = apiGatewayUrl + "/rides/delete/" + rideName;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(
                url, HttpMethod.DELETE, requestEntity, Object.class
        );

        return response.getBody();
    }
}