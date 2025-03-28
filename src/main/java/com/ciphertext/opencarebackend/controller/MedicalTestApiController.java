package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.MedicalTestResponse;
import com.ciphertext.opencarebackend.entity.MedicalTest;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.MedicalTestMapper;
import com.ciphertext.opencarebackend.service.MedicalTestService;
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
@RequestMapping("/api/medical-tests")
@RequiredArgsConstructor
public class MedicalTestApiController {
    private final MedicalTestService medicalTestService;
    private final MedicalTestMapper medicalTestMapper;

    @GetMapping
    public ResponseEntity<List<MedicalTestResponse>> getAllMedicalTests() {
        log.info("Retrieving all medical tests");

        List<MedicalTest> medicalTests = medicalTestService.getAllMedicalTests();
        List<MedicalTestResponse> medicalTestResponses = medicalTests.stream()
                .map(medicalTestMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(medicalTestResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalTestResponse> getMedicalTestById(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Retrieving medical test with ID: {}", id);

        MedicalTest medicalTest = medicalTestService.getMedicalTestById(id);
        MedicalTestResponse medicalTestResponse = medicalTestMapper.toResponse(medicalTest);

        return ResponseEntity.ok(medicalTestResponse);
    }
}
