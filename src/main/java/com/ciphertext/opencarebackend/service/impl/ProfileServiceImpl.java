package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.ProfileRepository;
import com.ciphertext.opencarebackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Override
    public Profile getProfileByKeycloakUserId(String keycloakUserId) throws ResourceNotFoundException {
        log.info("Fetching profile for Keycloak user ID: {}", keycloakUserId);
        return profileRepository.findByKeycloakUserId(keycloakUserId)
                .orElseThrow(() -> {
                    log.error("Profile not found for Keycloak user ID: {}", keycloakUserId);
                    return new ResourceNotFoundException("Profile not found for Keycloak user ID: " + keycloakUserId);
                });
    }

    @Override
    public Profile updateProfile(String keycloakUserId, Profile profile) {
        log.info("Updating profile for Keycloak user ID: {}", keycloakUserId);
        Profile existingProfile = getProfileByKeycloakUserId(keycloakUserId);
        existingProfile.setName(profile.getName());
        existingProfile.setEmail(profile.getEmail());
        existingProfile.setPhone(profile.getPhone());
        existingProfile.setDateOfBirth(profile.getDateOfBirth());
        existingProfile.setAddress(profile.getAddress());
        existingProfile.setDistrict(profile.getDistrict());
        existingProfile.setUpazila(profile.getUpazila());
        existingProfile.setUnion(profile.getUnion());
        existingProfile.setGender(profile.getGender());
        existingProfile.setDateOfBirth(profile.getDateOfBirth());
        return profileRepository.save(existingProfile);
    }

    @Override
    public Profile createProfile(Profile profile) {
        log.info("Creating new profile for Keycloak user ID: {}", profile.getKeycloakUserId());
        return profileRepository.save(profile);
    }
}
