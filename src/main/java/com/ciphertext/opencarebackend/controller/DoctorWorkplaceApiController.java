package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.DoctorWorkplaceRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorWorkplaceResponse;
import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.DoctorWorkplaceMapper;
import com.ciphertext.opencarebackend.service.DoctorWorkplaceService;
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
public class DoctorWorkplaceApiController {
    private final DoctorWorkplaceService doctorWorkplaceService;
    private final DoctorWorkplaceMapper doctorWorkplaceMapper;

    @GetMapping("/{doctorId}/workplaces")
    public ResponseEntity<List<DoctorWorkplaceResponse>> getDoctorWorkplacesByDoctorId(@PathVariable Long doctorId) {
        log.info("Retrieving all doctor workplaces");

        List<DoctorWorkplace> doctorWorkplaces = doctorWorkplaceService.getDoctorWorkplacesByDoctorId(doctorId);
        List<DoctorWorkplaceResponse> doctorWorkplaceResponses = doctorWorkplaces.stream()
                .map(doctorWorkplaceMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(doctorWorkplaceResponses);
    }

    @GetMapping("/{doctorId}/workplaces/{id}")
    public ResponseEntity<DoctorWorkplaceResponse> getDoctorWorkplaceById(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Retrieving doctor workplace with ID: {}", id);

        DoctorWorkplace doctorWorkplace = doctorWorkplaceService.getDoctorWorkplaceById(id);
        DoctorWorkplaceResponse doctorWorkplaceResponse = doctorWorkplaceMapper.toResponse(doctorWorkplace);

        return ResponseEntity.ok(doctorWorkplaceResponse);
    }

    @PostMapping("/{doctorId}/workplaces")
    public ResponseEntity<DoctorWorkplaceResponse> createDoctorWorkplace(@RequestBody DoctorWorkplaceRequest doctorWorkplaceRequest) {
        log.info("Creating doctor workplace");

        DoctorWorkplace doctorWorkplace = doctorWorkplaceMapper.toEntity(doctorWorkplaceRequest);
        DoctorWorkplace newDoctorWorkplace = doctorWorkplaceService.createDoctorWorkplace(doctorWorkplace);
        DoctorWorkplaceResponse doctorWorkplaceResponse = doctorWorkplaceMapper.toResponse(newDoctorWorkplace);

        return ResponseEntity.ok(doctorWorkplaceResponse);
    }

    @PutMapping("/{doctorId}/workplaces/{id}")
    public ResponseEntity<DoctorWorkplaceResponse> updateDoctorWorkplace(@RequestBody DoctorWorkplaceRequest doctorWorkplaceRequest, @PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Updating doctor workplace with ID: {}", id);

        DoctorWorkplace doctorWorkplace = doctorWorkplaceMapper.toEntity(doctorWorkplaceRequest);
        DoctorWorkplace updatedDoctorWorkplace = doctorWorkplaceService.updateDoctorWorkplace(doctorWorkplace, id);
        DoctorWorkplaceResponse doctorResponse = doctorWorkplaceMapper.toResponse(updatedDoctorWorkplace);

        return ResponseEntity.ok(doctorResponse);
    }

    @DeleteMapping("/{doctorId}/workplaces/{id}")
    public ResponseEntity<Void> deleteDoctorWorkplace(@PathVariable Long id)
            throws ResourceNotFoundException {
        log.info("Deleting doctor workplace with ID: {}", id);

        doctorWorkplaceService.deleteDoctorWorkplaceById(id);

        return ResponseEntity.noContent().build();
    }
}
