package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.InstitutionResponse;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.InstitutionMapper;
import com.ciphertext.opencarebackend.service.InstitutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
public class InstitutionApiController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @GetMapping
    public ResponseEntity<List<InstitutionResponse>> getAllInstitutions() {
        log.info("Retrieving all institutions");

        List<Institution> institutions = institutionService.getAllInstitutions();
        List<InstitutionResponse> institutionResponses = institutions.stream()
                .map(institutionMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(institutionResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InstitutionResponse> getInstitutionById(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Retrieving institution with ID: {}", id);

        Institution institution = institutionService.getInstitutionById(id);
        InstitutionResponse institutionResponse = institutionMapper.toResponse(institution);

        return ResponseEntity.ok(institutionResponse);
    }

}
