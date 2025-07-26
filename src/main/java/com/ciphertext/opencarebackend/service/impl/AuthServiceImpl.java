package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.request.KeycloakRegistrationRequest;
import com.ciphertext.opencarebackend.dto.request.RegistrationRequest;
import com.ciphertext.opencarebackend.entity.District;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.enums.BloodGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.UserType;
import com.ciphertext.opencarebackend.repository.DistrictRepository;
import com.ciphertext.opencarebackend.service.AuthService;
import com.ciphertext.opencarebackend.service.KeycloakService;
import com.ciphertext.opencarebackend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KeycloakService keycloakService;
    private final ProfileService profileService;
    private final DistrictRepository districtRepository;

    @Override
    public String registerUser(RegistrationRequest registrationRequest) {
        List<KeycloakRegistrationRequest.Credential> credentials = new ArrayList<>();
        KeycloakRegistrationRequest.Credential credential = new KeycloakRegistrationRequest.Credential();
        credential.setType("password");
        credential.setValue(registrationRequest.getPassword());
        credential.setTemporary(false);
        credentials.add(credential);
        KeycloakRegistrationRequest keycloakRequest =
                KeycloakRegistrationRequest.builder()
                        .email(registrationRequest.getEmail())
                        .firstName(registrationRequest.getFirstName())
                        .lastName(registrationRequest.getLastName())
                        .username(registrationRequest.getEmail())
                        .enabled(true)
                        .credentials(credentials)
                        .build();
        String userId = String.valueOf(keycloakService.registerUser(keycloakRequest).block());
        District district = districtRepository.findById(registrationRequest.getDistrictId()).orElse(null);
        Profile profile = new Profile();
        profile.setName(registrationRequest.getFirstName() + " " + registrationRequest.getLastName());
        profile.setBnName(registrationRequest.getFirstName() + " " + registrationRequest.getLastName());
        profile.setUserType(UserType.USER);
        profile.setEmail(registrationRequest.getEmail());
        profile.setUsername(registrationRequest.getEmail());
        profile.setDistrict(district);
        profile.setBloodGroup(BloodGroup.valueOf(registrationRequest.getBloodGroup()));
        profile.setGender(Gender.valueOf(registrationRequest.getGender()));
        profile.setKeycloakUserId(userId);
        profileService.createProfile(profile);
        return "User registered successfully with ID: " + userId;
    }
}
