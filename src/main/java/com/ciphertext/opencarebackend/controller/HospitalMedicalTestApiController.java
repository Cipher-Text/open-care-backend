package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.filter.DoctorFilter;
import com.ciphertext.opencarebackend.dto.filter.MedicalTestFilter;
import com.ciphertext.opencarebackend.dto.request.HospitalMedicalTestRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorResponse;
import com.ciphertext.opencarebackend.dto.response.HospitalMedicalTestResponse;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.HospitalMedicalTestMapper;
import com.ciphertext.opencarebackend.service.HospitalMedicalTestService;
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
@RequestMapping("/api/hospital-medical-tests")
@RequiredArgsConstructor
public class HospitalMedicalTestApiController {
    private final HospitalMedicalTestService hospitalMedicalTestService;
    private final HospitalMedicalTestMapper hospitalMedicalTestMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getMedicalTestsByHospitalId(
            @RequestParam(required = false) Integer hospitalId,
            @RequestParam(required = false) Integer medicalTestId,
            @RequestParam(required = false) Integer parentMedicalTestId,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);
        MedicalTestFilter medicalTestFilter = MedicalTestFilter.builder()
                .hospitalId(hospitalId)
                .medicalTestId(medicalTestId)
                .parentMedicalTestId(parentMedicalTestId)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
        Page<HospitalMedicalTest> pageHospitalMedicalTests = hospitalMedicalTestService.getPaginatedDataWithFilters(medicalTestFilter, pagingSort);

        Page<HospitalMedicalTestResponse> pageHospitalMedicalTestResponses = pageHospitalMedicalTests.map(hospitalMedicalTestMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("hospitalMedicalTests", pageHospitalMedicalTestResponses.getContent());
        response.put("currentPage", pageHospitalMedicalTestResponses.getNumber());
        response.put("totalItems", pageHospitalMedicalTestResponses.getTotalElements());
        response.put("totalPages", pageHospitalMedicalTestResponses.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalMedicalTestResponse> getHospitalMedicalTestById(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Retrieving hospital medical test with ID: {}", id);

        HospitalMedicalTest hospitalMedicalTest = hospitalMedicalTestService.getHospitalMedicalTestById(id);
        HospitalMedicalTestResponse hospitalMedicalTestResponse = hospitalMedicalTestMapper.toResponse(hospitalMedicalTest);

        return ResponseEntity.ok(hospitalMedicalTestResponse);
    }

    @PostMapping("")
    public ResponseEntity<HospitalMedicalTestResponse> createHospitalMedicalTest(@RequestBody HospitalMedicalTestRequest hospitalMedicalTestRequest) {
        log.info("Creating hospital medical test");

        HospitalMedicalTest hospitalMedicalTest = hospitalMedicalTestMapper.toEntity(hospitalMedicalTestRequest);
        HospitalMedicalTest newHospitalMedicalTest = hospitalMedicalTestService.createHospitalMedicalTest(hospitalMedicalTest);
        HospitalMedicalTestResponse hospitalMedicalTestResponse = hospitalMedicalTestMapper.toResponse(newHospitalMedicalTest);

        return ResponseEntity.ok(hospitalMedicalTestResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalMedicalTestResponse> updateHospitalMedicalTest(@RequestBody HospitalMedicalTestRequest hospitalMedicalTestRequest, @PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Updating hospital medical test with ID: {}", id);

        HospitalMedicalTest hospitalMedicalTest = hospitalMedicalTestMapper.toEntity(hospitalMedicalTestRequest);
        HospitalMedicalTest updatedHospitalMedicalTest = hospitalMedicalTestService.updateHospitalMedicalTest(hospitalMedicalTest, id);
        HospitalMedicalTestResponse hospitalResponse = hospitalMedicalTestMapper.toResponse(updatedHospitalMedicalTest);

        return ResponseEntity.ok(hospitalResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHospitalMedicalTest(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Deleting hospital medical test with ID: {}", id);

        hospitalMedicalTestService.deleteHospitalMedicalTestById(id);

        return ResponseEntity.noContent().build();
    }
}
