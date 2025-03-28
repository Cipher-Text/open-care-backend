package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.filter.InstitutionFilter;
import com.ciphertext.opencarebackend.dto.response.InstitutionResponse;
import com.ciphertext.opencarebackend.dto.response.InstitutionResponse;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.InstitutionMapper;
import com.ciphertext.opencarebackend.service.InstitutionService;
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
@RequestMapping("/api/institutions")
@RequiredArgsConstructor
public class InstitutionApiController {
    private final InstitutionService institutionService;
    private final InstitutionMapper institutionMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllInstitutionsPage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String bnName,
            @RequestParam(required = false) Integer enroll,
            @RequestParam(required = false) List<Integer> districtIds,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) List<String> hospitalTypes,
            @RequestParam(required = false) String organizationType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);
        InstitutionFilter institutionFilter = InstitutionFilter.builder()
                .name(name)
                .bnName(bnName)
                .districtIds(districtIds)
                .enroll(enroll)
                .organizationType(organizationType)
                .hospitalTypes(hospitalTypes)
                .country(country)
                .build();
        Page<Institution> pageInstitutions = institutionService.getPaginatedDataWithFilters(institutionFilter, pagingSort);

        Page<InstitutionResponse> pageInstitutionResponses = pageInstitutions.map(institutionMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("institutions", pageInstitutionResponses.getContent());
        response.put("currentPage", pageInstitutions.getNumber());
        response.put("totalItems", pageInstitutions.getTotalElements());
        response.put("totalPages", pageInstitutions.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
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
