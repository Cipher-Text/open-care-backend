package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.request.UserRegistrationRequest;
import com.ciphertext.opencarebackend.dto.response.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KeycloakService {

    private final RestTemplate restTemplate;
    private final String serverUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;

    public KeycloakService(
            RestTemplate restTemplate,
            @Value("${app.keycloak.server-url}") String serverUrl,
            @Value("${app.keycloak.realm}") String realm,
            @Value("${app.keycloak.client-id}") String clientId,
            @Value("${app.keycloak.client-secret}") String clientSecret) {
        this.restTemplate = restTemplate;
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public TokenResponse login(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        return restTemplate.postForObject(tokenUrl, request, TokenResponse.class);
    }

    public TokenResponse refreshToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        return restTemplate.postForObject(tokenUrl, request, TokenResponse.class);
    }

    public void logout(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", refreshToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String logoutUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/logout";
        restTemplate.postForEntity(logoutUrl, request, Void.class);
    }

    public boolean registerUser(UserRegistrationRequest registrationRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAdminToken());

        HttpEntity<UserRegistrationRequest> request = new HttpEntity<>(registrationRequest, headers);

        String usersUrl = serverUrl + "/admin/realms/" + realm + "/users";
        ResponseEntity<Void> response = restTemplate.postForEntity(usersUrl, request, Void.class);
        return response.getStatusCode().is2xxSuccessful();
    }

    public void resetPassword(String email) {
        String resetUrl = serverUrl + "/realms/" + realm + "/login-actions/reset-credentials";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("email", email);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        restTemplate.postForEntity(resetUrl, request, Void.class);
    }

    private String getAdminToken() {
        // Implementation to get admin token for Keycloak Admin API
        // Note: This requires proper admin credentials and restricted access
        // In production, consider using a service account with limited permissions
        return "admin-token";
    }

    public TokenResponse exchangeCodeForToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("code", code);
        map.add("redirect_uri", "http://your-frontend-app/callback");  // Must match your client configuration

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        return restTemplate.postForObject(tokenUrl, request, TokenResponse.class);
    }
}