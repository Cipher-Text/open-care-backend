package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.filter.HospitalFilter;
import com.ciphertext.opencarebackend.dto.request.HospitalRequest;
import com.ciphertext.opencarebackend.dto.response.HospitalResponse;
import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.HospitalMapper;
import com.ciphertext.opencarebackend.service.HospitalService;
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
@RequestMapping("/api/hospitals")
@RequiredArgsConstructor
public class HospitalApiController {
    private final HospitalService hospitalService;
    private final HospitalMapper hospitalMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllHospitalsPage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String bnName,
            @RequestParam(required = false) Integer numberOfBed,
            @RequestParam(required = false) List<Integer> districtIds,
            @RequestParam(required = false) Integer upazilaId,
            @RequestParam(required = false) Integer unionId,
            @RequestParam(required = false) List<String> hospitalTypes,
            @RequestParam(required = false) String organizationType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);
        HospitalFilter hospitalFilter = HospitalFilter.builder()
                .name(name)
                .bnName(bnName)
                .numberOfBed(numberOfBed)
                .districtIds(districtIds)
                .upazilaId(upazilaId)
                .unionId(unionId)
                .hospitalTypes(hospitalTypes)
                .organizationType(organizationType)
                .build();
        Page<Hospital> pageHospitals = hospitalService.getPaginatedDataWithFilters(hospitalFilter, pagingSort);

        Page<HospitalResponse> pageHospitalResponses = pageHospitals.map(hospitalMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("hospitals", pageHospitalResponses.getContent());
        response.put("currentPage", pageHospitals.getNumber());
        response.put("totalItems", pageHospitals.getTotalElements());
        response.put("totalPages", pageHospitals.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
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
