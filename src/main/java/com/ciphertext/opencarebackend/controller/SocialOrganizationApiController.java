package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.SocialOrganizationResponse;
import com.ciphertext.opencarebackend.entity.SocialOrganization;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.SocialOrganizationMapper;
import com.ciphertext.opencarebackend.service.SocialOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/social-organization")
@RequiredArgsConstructor
public class SocialOrganizationApiController {
    private final SocialOrganizationService socialOrganizationService;
    private final SocialOrganizationMapper socialOrganizationMapper;

    @GetMapping
    public ResponseEntity<List<SocialOrganizationResponse>> getAllSocialOrganizations() {
        log.info("Retrieving all social organization");

        List<SocialOrganization> socialOrganization = socialOrganizationService.getAllSocialOrganizations();
        List<SocialOrganizationResponse> socialOrganizationResponses = socialOrganization.stream()
                .map(socialOrganizationMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(socialOrganizationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SocialOrganizationResponse> getSocialOrganizationById(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Retrieving social organization with ID: {}", id);

        SocialOrganization socialOrganization = socialOrganizationService.getSocialOrganizationById(id);
        SocialOrganizationResponse socialOrganizationResponse = socialOrganizationMapper.toResponse(socialOrganization);

        return ResponseEntity.ok(socialOrganizationResponse);
    }

}
