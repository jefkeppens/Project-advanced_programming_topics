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
public class TicketService {

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;
    private final AuthService authService;

    private final RestTemplate restTemplate;

    public TicketService(AuthService authService) {
        this.restTemplate = new RestTemplate();
        this.authService = authService;
    }

    public List<Object> getAllTickets() {
        String url = apiGatewayUrl + "/tickets";

        // Prepare headers with the Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken()); // Add the Bearer token to the headers
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        // Send the GET request to the API Gateway
        ResponseEntity<List> response = restTemplate.exchange(
                url, HttpMethod.GET, requestEntity, List.class
        );

        // Return the response body (the list of tickets)
        return response.getBody();
    }
}