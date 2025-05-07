package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.filter.DoctorFilter;
import com.ciphertext.opencarebackend.dto.request.DoctorRequest;
import com.ciphertext.opencarebackend.dto.response.*;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.DoctorDegreeMapper;
import com.ciphertext.opencarebackend.mapper.DoctorMapper;
import com.ciphertext.opencarebackend.mapper.DoctorWorkplaceMapper;
import com.ciphertext.opencarebackend.service.DoctorDegreeService;
import com.ciphertext.opencarebackend.service.DoctorService;
import com.ciphertext.opencarebackend.service.DoctorWorkplaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorApiController {
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final DoctorWorkplaceService doctorWorkplaceService;
    private final DoctorWorkplaceMapper doctorWorkplaceMapper;
    private final DoctorDegreeService doctorDegreeService;
    private final DoctorDegreeMapper doctorDegreeMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllDoctorsPage(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String bnName,
            @RequestParam(required = false) String bmdcNo,
            @RequestParam(required = false) Boolean isCurrentWorkplace,
            @RequestParam(required = false) Integer hospitalId,
            @RequestParam(required = false) Integer workInstitutionId,
            @RequestParam(required = false) Integer studyInstitutionId,
            @RequestParam(required = false) Integer degreeId,
            @RequestParam(required = false) Integer specialityId,
            @RequestParam(required = false) Integer districtId,
            @RequestParam(required = false) Integer upazilaId,
            @RequestParam(required = false) Integer unionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);
        DoctorFilter doctorFilter = DoctorFilter.builder()
                .name(name)
                .bnName(bnName)
                .bmdcNo(bmdcNo)
                .isCurrentWorkplace(isCurrentWorkplace)
                .hospitalId(hospitalId)
                .studyInstitutionId(studyInstitutionId)
                .workInstitutionId(workInstitutionId)
                .degreeId(degreeId)
                .specialityId(specialityId)
                .districtId(districtId)
                .upazilaId(upazilaId)
                .unionId(unionId)
                .build();
        Page<Doctor> pageDoctors = doctorService.getPaginatedDataWithFilters(doctorFilter, pagingSort);

        Page<DoctorResponse> pageDoctorResponses = pageDoctors.map(doctorMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("doctors", pageDoctorResponses.getContent());
        response.put("currentPage", pageDoctors.getNumber());
        response.put("totalItems", pageDoctors.getTotalElements());
        response.put("totalPages", pageDoctors.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
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

        List<DoctorDegreeResponse> doctorDegreeResponses =
                doctorDegreeService.getDoctorDegreesByDoctorId(doctor.getId())
                        .stream().map(doctorDegreeMapper::toResponse).collect(Collectors.toList());
        List<DoctorWorkplaceResponse> doctorWorkplaceResponses =
                doctorWorkplaceService.getDoctorWorkplacesByDoctorId(doctor.getId())
                .stream().map(doctorWorkplaceMapper::toResponse).collect(Collectors.toList());
        doctorResponse.setDoctorDegrees(doctorDegreeResponses);
        doctorResponse.setDoctorWorkplaces(doctorWorkplaceResponses);
        // Handle degrees (abbreviations)
        if (!doctorDegreeResponses.isEmpty()) {
            Set<String> degreeAbbreviations = doctorDegreeResponses.stream()
                    .map(DoctorDegreeResponse::getDegree)
                    .filter(Objects::nonNull)
                    .map(DegreeResponse::getAbbreviation)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            if (!degreeAbbreviations.isEmpty()) {
                doctorResponse.setDegrees(String.join(", ", degreeAbbreviations));
            }
        }

        // Handle specializations (merge from both degrees and workplaces)
        Set<String> allSpecializations = new LinkedHashSet<>();

        // Add specializations from degrees
        doctorDegreeResponses.stream()
                .map(DoctorDegreeResponse::getMedicalSpeciality)
                .filter(Objects::nonNull)
                .map(MedicalSpecialityResponse::getName)
                .filter(Objects::nonNull)
                .forEach(allSpecializations::add);

        // Add specializations from workplaces
        doctorWorkplaceResponses.stream()
                .map(DoctorWorkplaceResponse::getMedicalSpeciality)
                .filter(Objects::nonNull)
                .map(MedicalSpecialityResponse::getName)
                .filter(Objects::nonNull)
                .forEach(allSpecializations::add);

        if (!allSpecializations.isEmpty()) {
            doctorResponse.setSpecializations(String.join(", ", allSpecializations));
        }

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
