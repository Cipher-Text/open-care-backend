package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.UserProfile;
import com.ciphertext.opencarebackend.dto.UserProfileUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;

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

    @PutMapping("/profile")
    public ResponseEntity<String> updateUserProfile(
            @RequestBody UserProfileUpdate profileUpdate,
            Authentication authentication) {
        // Implementation for updating user profile
        // You would use KeycloakAdminClient to update user details
        return ResponseEntity.ok("Profile updated successfully");
    }
}