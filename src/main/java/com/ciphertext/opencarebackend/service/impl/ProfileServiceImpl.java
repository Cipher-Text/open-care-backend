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
@Transactional
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
    public Profile getProfileById(Long id) throws ResourceNotFoundException {
        log.info("Fetching profile with ID: {}", id);
        return profileRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Profile not found with ID: {}", id);
                    return new ResourceNotFoundException("Profile not found with ID: " + id);
                });
    }

    @Override
    public Profile updateProfileBykeycloakId(String keycloakUserId, Profile profile) {
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
    public Profile updateProfile(Long id, Profile profile) {
        log.info("Updating profile with ID: {}", id);
        Profile existingProfile = getProfileById(id);
        existingProfile.setName(profile.getName());
        existingProfile.setBnName(profile.getBnName());
        existingProfile.setImageUrl(profile.getImageUrl());
        existingProfile.setUsername(profile.getUsername());
        existingProfile.setKeycloakUserId(profile.getKeycloakUserId());
        existingProfile.setAddress(profile.getAddress());
        existingProfile.setUserType(profile.getUserType());
        existingProfile.setEmail(profile.getEmail());
        existingProfile.setPhone(profile.getPhone());
        existingProfile.setDateOfBirth(profile.getDateOfBirth());
        existingProfile.setAddress(profile.getAddress());
        existingProfile.setDistrict(profile.getDistrict());
        existingProfile.setUpazila(profile.getUpazila());
        existingProfile.setUnion(profile.getUnion());
        existingProfile.setGender(profile.getGender());
        return profileRepository.save(existingProfile);
    }

    @Override
    public Profile createProfile(Profile profile) {
        log.info("Creating new profile for Keycloak user ID: {}", profile.getKeycloakUserId());
        return profileRepository.save(profile);
    }
}
