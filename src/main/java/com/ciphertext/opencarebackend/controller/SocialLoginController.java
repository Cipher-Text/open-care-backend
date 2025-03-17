package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.TokenResponse;
import com.ciphertext.opencarebackend.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/social")
public class SocialLoginController {

    @Value("${app.keycloak.server-url}")
    private String keycloakServerUrl;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.client-id}")
    private String clientId;

    @Value("${app.frontend.redirect-uri}")
    private String frontendRedirectUri;

    private final KeycloakService keycloakService;

    @GetMapping("/redirect/{provider}")
    public ResponseEntity<Map<String, String>> getAuthorizationUrl(@PathVariable String provider) {
        String authUrl = keycloakServerUrl +
                "/realms/" + realm +
                "/protocol/openid-connect/auth" +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&redirect_uri=" + frontendRedirectUri +
                "&scope=openid" +
                "&kc_idp_hint=" + provider;

        Map<String, String> response = new HashMap<>();
        response.put("authorizationUrl", authUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/callback")
    public ResponseEntity<TokenResponse> handleCallback(
            @RequestParam("code") String code,
            @RequestParam("session_state") String sessionState) {

        // Exchange authorization code for token
        TokenResponse tokenResponse = keycloakService.exchangeCodeForToken(code).block();
        return ResponseEntity.ok(tokenResponse);
    }
}