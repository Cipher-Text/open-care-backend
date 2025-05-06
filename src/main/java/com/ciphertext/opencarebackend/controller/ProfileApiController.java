package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.service.MinioService;
import com.ciphertext.opencarebackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileApiController {

    private final ProfileService profileService;
    private final MinioService minioService;

    @PostMapping(value = "/{id}/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> uploadProfileImage(@PathVariable Long id,
                                                     @RequestParam("photo") MultipartFile photo) {
        Profile profile = profileService.getProfileById(id);

        // Delete old photo if exists
        if (profile.getImageUrl() != null) {
            minioService.deleteFile(profile.getImageUrl());
        }

        // Upload new photo
        String objectName = minioService.uploadFile(photo, profile.getId().toString());

        // Update profile with new photo URL
        profile.setImageUrl(objectName);
        profileService.updateProfile(profile.getId(), profile);

        // Get presigned URL for immediate display
        String url = minioService.getPresignedUrl(objectName, 3600);

        Map<String, String> response = new HashMap<>();
        response.put("photoUrl", objectName);
        response.put("presignedUrl", url);

        return ResponseEntity.ok(response);
    }
}
