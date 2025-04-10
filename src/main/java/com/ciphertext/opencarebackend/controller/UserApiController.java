package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.UserProfileUpdate;
import com.ciphertext.opencarebackend.dto.request.ProfileRequest;
import com.ciphertext.opencarebackend.dto.response.ProfileResponse;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.mapper.ProfileMapper;
import com.ciphertext.opencarebackend.service.KeycloakAdminService;
import com.ciphertext.opencarebackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final KeycloakAdminService keycloakAdminService;


    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getUserProfile(Authentication authentication) {
        // Extract user info from the JWT token
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Profile profile = profileService.getProfileByKeycloakUserId(jwt.getSubject());
        ProfileResponse profileResponse = profileMapper.toResponse(profile);

        UserRepresentation userRepresentation = keycloakAdminService.getUserById(jwt.getSubject());

        log.info("User representation: {}", userRepresentation);

        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/roles")
    public ResponseEntity<Map<String, Object>> getUserRoles(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Authentication required"));
        }

        Map<String, Object> response = new HashMap<>();
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess != null && realmAccess.containsKey("roles")) {
            response.put("roles", realmAccess.get("roles"));
        } else {
            response.put("roles", List.of());
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(
            @RequestBody ProfileRequest profileRequest,
            Authentication authentication) {
        // Extract user info from the JWT token
        Jwt jwt = (Jwt) authentication.getPrincipal();
        String keycloakUserId = jwt.getSubject();
        log.info("Updating profile for Keycloak user ID: {}", keycloakUserId);
        UserRepresentation userRepresentation = keycloakAdminService.getUserById(keycloakUserId);
        userRepresentation.setUsername(profileRequest.getUsername());
        userRepresentation.setFirstName(profileRequest.getFirstName());
        userRepresentation.setLastName(profileRequest.getLastName());
        userRepresentation.setEmail(profileRequest.getEmail());
        keycloakAdminService.updateUser(keycloakUserId, userRepresentation);
        Profile profile = profileService.getProfileByKeycloakUserId(keycloakUserId);
        profile.setUsername(profileRequest.getUsername());
        profile.setName(profileRequest.getFirstName() + " " + profileRequest.getLastName());
        profile.setEmail(profileRequest.getEmail());
        profile.setPhone(profileRequest.getPhone());
        profile.setAddress(profileRequest.getAddress());
        profile.setGender(Gender.valueOf(profileRequest.getGender()));
        profile.setDateOfBirth(profileRequest.getDateOfBirth());
        return ResponseEntity.ok("Profile updated successfully");
    }
}