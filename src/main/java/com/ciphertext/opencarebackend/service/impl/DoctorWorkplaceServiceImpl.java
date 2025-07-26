package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.response.MedicalSpecialityResponse;
import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.repository.DoctorWorkplaceRepository;
import com.ciphertext.opencarebackend.service.DoctorWorkplaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DoctorWorkplaceServiceImpl implements DoctorWorkplaceService {
    private final DoctorWorkplaceRepository doctorWorkplaceRepository;

    @Override
    public List<DoctorWorkplace> getDoctorWorkplacesByDoctorId(Long doctorId) {
        log.info("Fetching all doctor workplaces");
        List<DoctorWorkplace> doctorWorkplaces = doctorWorkplaceRepository.findByDoctorId(doctorId);
        log.info("Retrieved {} doctor workplaces", doctorWorkplaces.size());
        return doctorWorkplaces;
    }

    @Override
    public DoctorWorkplace getDoctorWorkplaceById(Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid doctor workplace ID: {}", id);
            throw new BadRequestException("Doctor Workplace ID must be positive");
        }

        log.info("Fetching doctor workplace with id: {}", id);
        return doctorWorkplaceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor Workplace not found with id: {}", id);
                    return new ResourceNotFoundException("Doctor Workplace not found with id: " + id);
                });
    }

    @Override
    public DoctorWorkplace createDoctorWorkplace(DoctorWorkplace doctorWorkplace) {
        log.info("Creating doctor workplace: {}", doctorWorkplace);
        return doctorWorkplaceRepository.save(doctorWorkplace);
    }

    @Override
    public DoctorWorkplace updateDoctorWorkplace(DoctorWorkplace newDoctorWorkplace, Long doctorWorkplaceId) {
        log.info("Updating doctor workplace: {}", newDoctorWorkplace);
        return doctorWorkplaceRepository.findById(doctorWorkplaceId)
                .map(doctorWorkplace -> {
                    doctorWorkplace.setDoctor(newDoctorWorkplace.getDoctor());
                    doctorWorkplace.setInstitution(newDoctorWorkplace.getInstitution());
                    doctorWorkplace.setMedicalSpeciality(newDoctorWorkplace.getMedicalSpeciality());
                    doctorWorkplace.setDoctorPosition(newDoctorWorkplace.getDoctorPosition());
                    doctorWorkplace.setTeacherPosition(newDoctorWorkplace.getTeacherPosition());
                    doctorWorkplace.setStartDate(newDoctorWorkplace.getStartDate());
                    doctorWorkplace.setEndDate(newDoctorWorkplace.getEndDate());
                    return doctorWorkplaceRepository.save(doctorWorkplace);
                })
                .orElseGet(() -> {
                    newDoctorWorkplace.setId(doctorWorkplaceId);
                    return doctorWorkplaceRepository.save(newDoctorWorkplace);
                });
    }

    @Override
    public ResponseEntity<Object> deleteDoctorWorkplaceById(Long doctorWorkplaceId) {
        log.info("Deleting doctor workplace with id: {}", doctorWorkplaceId);
        doctorWorkplaceRepository.deleteById(doctorWorkplaceId);
        if (doctorWorkplaceRepository.findById(doctorWorkplaceId).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
        } else return ResponseEntity.ok().body("Doctor Workplace is Deleted Successfully");
    }

    @Override
    public List<MedicalSpecialityResponse> getTopMedicalSpecialities(Integer limit) {
        List<Object[]> results = doctorWorkplaceRepository.findMedicalSpecialitiesWithDoctorCount(limit);
        return results.stream()
                .map(this::mapToMedicalSpecialityResponse)
                .collect(Collectors.toList());
    }

    private MedicalSpecialityResponse mapToMedicalSpecialityResponse(Object[] result) {
        MedicalSpecialityResponse response = new MedicalSpecialityResponse();
        response.setId((Integer) result[0]);
        response.setName((String) result[1]);
        response.setBnName((String) result[2]);
        response.setIcon((String) result[3]);
        response.setDoctorCount(((Long) result[4]));
        return response;
    }

}
