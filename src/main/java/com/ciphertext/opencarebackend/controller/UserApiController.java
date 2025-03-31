package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.UserProfile;
import com.ciphertext.opencarebackend.dto.UserProfileUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile(Authentication authentication) {
        // Extract user info from the JWT token
        Jwt jwt = (Jwt) authentication.getPrincipal();

        UserProfile profile = new UserProfile();
        profile.setId(jwt.getSubject());
        profile.setUsername((String) jwt.getClaims().get("preferred_username"));
        profile.setEmail((String) jwt.getClaims().get("email"));
        profile.setFirstName((String) jwt.getClaims().get("given_name"));
        profile.setLastName((String) jwt.getClaims().get("family_name"));

        return ResponseEntity.ok(profile);
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