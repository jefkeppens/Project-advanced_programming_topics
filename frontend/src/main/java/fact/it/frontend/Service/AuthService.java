package fact.it.frontend.Service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
