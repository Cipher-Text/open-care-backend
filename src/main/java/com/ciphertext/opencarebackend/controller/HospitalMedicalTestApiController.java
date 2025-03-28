package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.HospitalMedicalTestRequest;
import com.ciphertext.opencarebackend.dto.response.HospitalMedicalTestResponse;
import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.HospitalMedicalTestMapper;
import com.ciphertext.opencarebackend.service.HospitalMedicalTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalMedicalTestApiController {
    private final HospitalMedicalTestService hospitalMedicalTestService;
    private final HospitalMedicalTestMapper hospitalMedicalTestMapper;

    @GetMapping("/{hospitalId}/medical-tests")
    public ResponseEntity<List<HospitalMedicalTestResponse>> getHospitalMedicalTestsByDoctorId(@PathVariable Long hospitalId) {
        log.info("Retrieving all hospital medical tests");

        List<HospitalMedicalTest> hospitalMedicalTests = hospitalMedicalTestService.getHospitalMedicalTestsByDoctorId(hospitalId);
        List<HospitalMedicalTestResponse> hospitalMedicalTestResponses = hospitalMedicalTests.stream()
                .map(hospitalMedicalTestMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(hospitalMedicalTestResponses);
    }

    @GetMapping("/{hospitalId}/medical-tests/{id}")
    public ResponseEntity<HospitalMedicalTestResponse> getHospitalMedicalTestById(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Retrieving hospital medical test with ID: {}", id);

        HospitalMedicalTest hospitalMedicalTest = hospitalMedicalTestService.getHospitalMedicalTestById(id);
        HospitalMedicalTestResponse hospitalMedicalTestResponse = hospitalMedicalTestMapper.toResponse(hospitalMedicalTest);

        return ResponseEntity.ok(hospitalMedicalTestResponse);
    }

    @PostMapping("/{hospitalId}/medical-tests")
    public ResponseEntity<HospitalMedicalTestResponse> createHospitalMedicalTest(@RequestBody HospitalMedicalTestRequest hospitalMedicalTestRequest) {
        log.info("Creating hospital medical test");

        HospitalMedicalTest hospitalMedicalTest = hospitalMedicalTestMapper.toEntity(hospitalMedicalTestRequest);
        HospitalMedicalTest newHospitalMedicalTest = hospitalMedicalTestService.createHospitalMedicalTest(hospitalMedicalTest);
        HospitalMedicalTestResponse hospitalMedicalTestResponse = hospitalMedicalTestMapper.toResponse(newHospitalMedicalTest);

        return ResponseEntity.ok(hospitalMedicalTestResponse);
    }

    @PutMapping("/{hospitalId}/medical-tests/{id}")
    public ResponseEntity<HospitalMedicalTestResponse> updateHospitalMedicalTest(@RequestBody HospitalMedicalTestRequest hospitalMedicalTestRequest, @PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Updating hospital medical test with ID: {}", id);

        HospitalMedicalTest hospitalMedicalTest = hospitalMedicalTestMapper.toEntity(hospitalMedicalTestRequest);
        HospitalMedicalTest updatedHospitalMedicalTest = hospitalMedicalTestService.updateHospitalMedicalTest(hospitalMedicalTest, id);
        HospitalMedicalTestResponse hospitalResponse = hospitalMedicalTestMapper.toResponse(updatedHospitalMedicalTest);

        return ResponseEntity.ok(hospitalResponse);
    }

    @DeleteMapping("/{hospitalId}/medical-tests/{id}")
    public ResponseEntity<Void> deleteHospitalMedicalTest(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Deleting hospital medical test with ID: {}", id);

        hospitalMedicalTestService.deleteHospitalMedicalTestById(id);

        return ResponseEntity.noContent().build();
    }
}
