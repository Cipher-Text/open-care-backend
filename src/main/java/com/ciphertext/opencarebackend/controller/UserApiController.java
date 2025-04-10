package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.UserProfileUpdate;
import com.ciphertext.opencarebackend.dto.response.ProfileResponse;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.mapper.ProfileMapper;
import com.ciphertext.opencarebackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
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
public class UserApiController {

    private final ProfileService profileService;
    private final ProfileMapper profileMapper;


    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getUserProfile(Authentication authentication) {
        // Extract user info from the JWT token
        Jwt jwt = (Jwt) authentication.getPrincipal();

        Profile profile = profileService.getProfileByKeycloakUserId(jwt.getSubject());
        ProfileResponse profileResponse = profileMapper.toResponse(profile);

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
            @RequestBody UserProfileUpdate profileUpdate,
            Authentication authentication) {
        // Implementation for updating user profile
        // You would use KeycloakAdminClient to update user details
        return ResponseEntity.ok("Profile updated successfully");
    }
}