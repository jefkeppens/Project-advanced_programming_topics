package fact.it.frontend.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PersonService {

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;
    private final AuthService authService;

    private final RestTemplate restTemplate;

    public PersonService(AuthService authService) {
        this.restTemplate = new RestTemplate();
        this.authService = authService;
    }

    public List<Object> getAllPeople() {
        String url = apiGatewayUrl + "/people";

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

    public Object addPerson(Object personRequest) {
        String url = apiGatewayUrl + "/people/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());

        HttpEntity<Object> requestEntity = new HttpEntity<>(personRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, requestEntity, String.class // Expect plain text or generic response
        );

        return response.getBody(); // Return raw response as needed
    }

    public Object deletePerson(String personEmail) {
        String url = apiGatewayUrl + "/people/delete/" + personEmail;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authService.getToken());

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.DELETE, requestEntity, String.class // Expect plain text or generic response
        );

        return response.getBody(); // Return raw response as needed
    }

}