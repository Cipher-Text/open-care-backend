package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.DoctorRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorResponse;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.DoctorMapper;
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
public class DoctorApiController {
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping
    public ResponseEntity<List<DoctorResponse>> getAllDoctors() {
        log.info("Retrieving all doctors");

        List<Doctor> doctors = doctorService.getAllDoctors();
        List<DoctorResponse> doctorResponses = doctors.stream()
                .map(doctorMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponse> getDoctorById(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Retrieving doctor with ID: {}", id);

        Doctor doctor = doctorService.getDoctorById(id);
        DoctorResponse doctorResponse = doctorMapper.toResponse(doctor);

        return ResponseEntity.ok(doctorResponse);
    }

    @PostMapping
    public ResponseEntity<DoctorResponse> createDoctor(@RequestBody DoctorRequest doctorRequest) {
        log.info("Creating doctor");

        Doctor doctor = doctorMapper.toEntity(doctorRequest);
        Doctor newDoctor = doctorService.createDoctor(doctor);
        DoctorResponse doctorResponse = doctorMapper.toResponse(newDoctor);

        return ResponseEntity.ok(doctorResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponse> updateDoctor(@RequestBody DoctorRequest doctorRequest, @PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Updating doctor with ID: {}", id);

        Doctor doctor = doctorMapper.toEntity(doctorRequest);
        Doctor updatedDoctor = doctorService.updateDoctor(doctor, id);
        DoctorResponse doctorResponse = doctorMapper.toResponse(updatedDoctor);

        return ResponseEntity.ok(doctorResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Deleting doctor with ID: {}", id);

        doctorService.deleteDoctorById(id);

        return ResponseEntity.noContent().build();
    }
}
