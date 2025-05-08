package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;

public interface ProfileService {
    Long getProfileCount();
    Profile getProfileByKeycloakUserId(String keycloakUserId) throws ResourceNotFoundException;
    Profile getProfileById(Long id) throws ResourceNotFoundException;
    Profile updateProfileBykeycloakId(String keycloakUserId, Profile profile);
    Profile updateProfile(Long id, Profile profile);
    Profile createProfile(Profile profile);
}
