package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.request.UserRegistrationRequest;
import com.ciphertext.opencarebackend.dto.response.TokenResponse;
import com.ciphertext.opencarebackend.exception.KeycloakClientException;
import com.ciphertext.opencarebackend.exception.KeycloakServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KeycloakService {

    private final WebClient webClient;
    private final String serverUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;

    public KeycloakService(
            WebClient.Builder webClientBuilder,
            @Value("${app.keycloak.server-url}") String serverUrl,
            @Value("${app.keycloak.realm}") String realm,
            @Value("${app.keycloak.client-id}") String clientId,
            @Value("${app.keycloak.client-secret}") String clientSecret,
            @Value("${app.keycloak.admin.client-id}") String adminClientId,
            @Value("${app.keycloak.admin.username}") String adminUsername,
            @Value("${app.keycloak.admin.password}") String adminPassword
            ) {
        this.webClient = webClientBuilder.build();
        this.serverUrl = serverUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public Mono<TokenResponse> login(String username, String password) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", username);
        formData.add("password", password);

        return executeTokenRequest(formData);
    }

    public Mono<TokenResponse> refreshToken(String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "refresh_token");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        return executeTokenRequest(formData);
    }

    public Mono<Void> logout(String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);

        String logoutUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/logout";

        return executeRequest(logoutUrl, formData, Void.class);
    }

    public Mono<Boolean> registerUser(UserRegistrationRequest registrationRequest) {
        return getAdminToken().flatMap(adminToken -> {
            String usersUrl = serverUrl + "/admin/realms/" + realm + "/users";

            return webClient.post()
                    .uri(usersUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken)
                    .body(BodyInserters.fromValue(registrationRequest))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                    .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                    .toBodilessEntity()
                    .map(response -> response.getStatusCode().is2xxSuccessful())
                    .onErrorReturn(false);
        });
    }

    public Mono<Void> resetPassword(String email) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("email", email);

        String resetUrl = serverUrl + "/realms/" + realm + "/login-actions/reset-credentials";

        return executeRequest(resetUrl, formData, Void.class);
    }

    private Mono<String> getAdminToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", "admin-cli");
        formData.add("username", "admin");
        formData.add("password", "admin");
        formData.add("grant_type", "password");

        String tokenUrl = serverUrl + "/realms/master/protocol/openid-connect/token";

        return executeRequest(tokenUrl, formData, TokenResponse.class)
                .map(TokenResponse::getAccess_token);
    }

    public Mono<TokenResponse> exchangeCodeForToken(String code) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("code", code);
        formData.add("redirect_uri", "http://your-frontend-app/callback");  // Must match your client configuration

        return executeTokenRequest(formData);
    }

    // Helper methods to reduce duplication
    private Mono<TokenResponse> executeTokenRequest(MultiValueMap<String, String> formData) {
        String tokenUrl = serverUrl + "/realms/" + realm + "/protocol/openid-connect/token";
        return executeRequest(tokenUrl, formData, TokenResponse.class);
    }

    private <T> Mono<T> executeRequest(String url, MultiValueMap<String, String> formData, Class<T> responseType) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, this::handleClientError)
                .onStatus(HttpStatusCode::is5xxServerError, this::handleServerError)
                .bodyToMono(responseType);
    }

    private Mono<Throwable> handleClientError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new KeycloakClientException("Keycloak client error: " + response.statusCode() + " " + body)));
    }

    private Mono<Throwable> handleServerError(ClientResponse response) {
        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new KeycloakServerException("Keycloak server error: " + response.statusCode() + " " + body)));
    }
}