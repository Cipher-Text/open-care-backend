package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.HospitalRequest;
import com.ciphertext.opencarebackend.dto.response.HospitalResponse;
import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.HospitalMapper;
import com.ciphertext.opencarebackend.service.HospitalService;
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
public class HospitalApiController {
    private final HospitalService hospitalService;
    private final HospitalMapper hospitalMapper;

    @GetMapping
    public ResponseEntity<List<HospitalResponse>> getAllHospitals() {
        log.info("Retrieving all hospitals");

        List<Hospital> hospitals = hospitalService.getAllHospitals();
        List<HospitalResponse> hospitalResponses = hospitals.stream()
                .map(hospitalMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(hospitalResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponse> getHospitalById(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Retrieving hospital with ID: {}", id);

        Hospital hospital = hospitalService.getHospitalById(id);
        HospitalResponse hospitalResponse = hospitalMapper.toResponse(hospital);

        return ResponseEntity.ok(hospitalResponse);
    }

    @PostMapping
    public ResponseEntity<HospitalResponse> createHospital(@RequestBody HospitalRequest hospitalRequest) {
        log.info("Creating hospital");

        Hospital hospital = hospitalMapper.toEntity(hospitalRequest);
        Hospital newHospital = hospitalService.createHospital(hospital);
        HospitalResponse hospitalResponse = hospitalMapper.toResponse(newHospital);

        return ResponseEntity.ok(hospitalResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponse> updateHospital(@RequestBody HospitalRequest hospitalRequest, @PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Updating hospital with ID: {}", id);

        Hospital hospital = hospitalMapper.toEntity(hospitalRequest);
        Hospital updatedHospital = hospitalService.updateHospital(hospital, id);
        HospitalResponse hospitalResponse = hospitalMapper.toResponse(updatedHospital);

        return ResponseEntity.ok(hospitalResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospital(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Deleting hospital with ID: {}", id);

        hospitalService.deleteHospitalById(id);

        return ResponseEntity.noContent().build();
    }
}
