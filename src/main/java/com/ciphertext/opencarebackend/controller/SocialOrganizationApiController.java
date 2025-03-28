package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.filter.SocialOrganizationFilter;
import com.ciphertext.opencarebackend.dto.response.SocialOrganizationResponse;
import com.ciphertext.opencarebackend.dto.response.SocialOrganizationResponse;
import com.ciphertext.opencarebackend.entity.SocialOrganization;
import com.ciphertext.opencarebackend.entity.SocialOrganization;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.SocialOrganizationMapper;
import com.ciphertext.opencarebackend.service.SocialOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/social-organization")
@RequiredArgsConstructor
public class SocialOrganizationApiController {
    private final SocialOrganizationService socialOrganizationService;
    private final SocialOrganizationMapper socialOrganizationMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllSocialOrganizationsPage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String bnName,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String socialOrganizationType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);
        SocialOrganizationFilter socialOrganizationFilter = SocialOrganizationFilter.builder()
                .name(name)
                .bnName(bnName)
                .phone(phone)
                .email(email)
                .socialOrganizationType(socialOrganizationType)
                .build();
        Page<SocialOrganization> pageSocialOrganizations = socialOrganizationService.getPaginatedDataWithFilters(socialOrganizationFilter, pagingSort);

        Page<SocialOrganizationResponse> pageSocialOrganizationResponses = pageSocialOrganizations.map(socialOrganizationMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("socialOrganizations", pageSocialOrganizationResponses.getContent());
        response.put("currentPage", pageSocialOrganizations.getNumber());
        response.put("totalItems", pageSocialOrganizations.getTotalElements());
        response.put("totalPages", pageSocialOrganizations.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
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
