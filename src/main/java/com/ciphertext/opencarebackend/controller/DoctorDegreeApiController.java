package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.DoctorDegreeRequest;
import com.ciphertext.opencarebackend.dto.request.DoctorRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorDegreeResponse;
import com.ciphertext.opencarebackend.dto.response.DoctorResponse;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.entity.DoctorDegree;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.DoctorDegreeMapper;
import com.ciphertext.opencarebackend.mapper.DoctorMapper;
import com.ciphertext.opencarebackend.service.DoctorDegreeService;
import com.ciphertext.opencarebackend.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorDegreeApiController {
    private final DoctorDegreeService doctorDegreeService;
    private final DoctorDegreeMapper doctorDegreeMapper;

    @GetMapping("/{doctorId}/degrees")
    public ResponseEntity<List<DoctorDegreeResponse>> getDoctorDegreesByDoctorId(@PathVariable Long doctorId) {
        log.info("Retrieving all doctor degrees");

        List<DoctorDegree> doctorDegrees = doctorDegreeService.getDoctorDegreesByDoctorId(doctorId);
        List<DoctorDegreeResponse> doctorDegreeResponses = doctorDegrees.stream()
                .map(doctorDegreeMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorDegreeResponses);
    }

    @GetMapping("/{doctorId}/degrees/{id}")
    public ResponseEntity<DoctorDegreeResponse> getDoctorDegreeById(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Retrieving doctor degree with ID: {}", id);

        DoctorDegree doctorDegree = doctorDegreeService.getDoctorDegreeById(id);
        DoctorDegreeResponse doctorDegreeResponse = doctorDegreeMapper.toResponse(doctorDegree);

        return ResponseEntity.ok(doctorDegreeResponse);
    }

    @PostMapping("/{doctorId}/degrees")
    public ResponseEntity<DoctorDegreeResponse> createDoctorDegree(@RequestBody DoctorDegreeRequest doctorDegreeRequest) {
        log.info("Creating doctor degree");

        DoctorDegree doctorDegree = doctorDegreeMapper.toEntity(doctorDegreeRequest);
        DoctorDegree newDoctorDegree = doctorDegreeService.createDoctorDegree(doctorDegree);
        DoctorDegreeResponse doctorDegreeResponse = doctorDegreeMapper.toResponse(newDoctorDegree);

        return ResponseEntity.ok(doctorDegreeResponse);
    }

    @PutMapping("/{doctorId}/degrees/{id}")
    public ResponseEntity<DoctorDegreeResponse> updateDoctorDegree(@RequestBody DoctorDegreeRequest doctorDegreeRequest, @PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Updating doctor degree with ID: {}", id);

        DoctorDegree doctorDegree = doctorDegreeMapper.toEntity(doctorDegreeRequest);
        DoctorDegree updatedDoctorDegree = doctorDegreeService.updateDoctorDegree(doctorDegree, id);
        DoctorDegreeResponse doctorResponse = doctorDegreeMapper.toResponse(updatedDoctorDegree);

        return ResponseEntity.ok(doctorResponse);
    }

    @DeleteMapping("/{doctorId}/degrees/{id}")
    public ResponseEntity<Void> deleteDoctorDegree(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Deleting doctor degree with ID: {}", id);

        doctorDegreeService.deleteDoctorDegreeById(id);

        return ResponseEntity.noContent().build();
    }
}
