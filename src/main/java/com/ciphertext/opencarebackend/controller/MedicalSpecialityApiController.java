package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.MedicalSpecialityResponse;
import com.ciphertext.opencarebackend.entity.MedicalSpeciality;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.MedicalSpecialityMapper;
import com.ciphertext.opencarebackend.service.MedicalSpecialityService;
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
@RequestMapping("/api/medical-specialities")
@RequiredArgsConstructor
public class MedicalSpecialityApiController {
    private final MedicalSpecialityService medicalSpecialityService;
    private final MedicalSpecialityMapper medicalSpecialityMapper;

    @GetMapping
    public ResponseEntity<List<MedicalSpecialityResponse>> getAllmedicalSpecialities() {
        log.info("Retrieving all medical tests");

        List<MedicalSpeciality> medicalSpecialities = medicalSpecialityService.getAllSpecialities();
        List<MedicalSpecialityResponse> medicalSpecialityResponses = medicalSpecialities.stream()
                .map(medicalSpecialityMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(medicalSpecialityResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalSpecialityResponse> getMedicalSpecialityById(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Retrieving medical test with ID: {}", id);

        MedicalSpeciality medicalSpeciality = medicalSpecialityService.getSpecialityById(id);
        MedicalSpecialityResponse medicalSpecialityResponse = medicalSpecialityMapper.toResponse(medicalSpeciality);

        return ResponseEntity.ok(medicalSpecialityResponse);
    }
}
