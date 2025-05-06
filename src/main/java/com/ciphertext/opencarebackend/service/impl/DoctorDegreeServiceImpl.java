package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.DoctorDegree;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.DoctorDegreeRepository;
import com.ciphertext.opencarebackend.service.DoctorDegreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DoctorDegreeServiceImpl implements DoctorDegreeService {
    private final DoctorDegreeRepository doctorDegreeRepository;

    @Override
    public List<DoctorDegree> getDoctorDegreesByDoctorId(Long doctorId) {
        log.info("Fetching all doctor degrees");
        List<DoctorDegree> doctorDegrees = doctorDegreeRepository.findByDoctorId(doctorId);
        log.info("Retrieved {} doctor degrees", doctorDegrees.size());
        return doctorDegrees;
    }

    @Override
    public DoctorDegree getDoctorDegreeById(Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid doctorDegree ID: {}", id);
            throw new BadRequestException("DoctorDegree ID must be positive");
        }

        log.info("Fetching doctor degree with id: {}", id);
        return doctorDegreeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("DoctorDegree not found with id: {}", id);
                    return new ResourceNotFoundException("DoctorDegree not found with id: " + id);
                });
    }

    @Override
    public DoctorDegree createDoctorDegree(DoctorDegree doctorDegree) {
        log.info("Creating doctorDegree: {}", doctorDegree);
        return doctorDegreeRepository.save(doctorDegree);
    }

    @Override
    public DoctorDegree updateDoctorDegree(DoctorDegree newDoctorDegree, Long doctorDegreeId) {
        log.info("Updating doctorDegree: {}", newDoctorDegree);
        return doctorDegreeRepository.findById(doctorDegreeId)
                .map(doctorDegree -> {
                    doctorDegree.setDoctor(newDoctorDegree.getDoctor());
                    doctorDegree.setDescription(newDoctorDegree.getDescription());
                    doctorDegree.setDegree(newDoctorDegree.getDegree());
                    doctorDegree.setInstitution(newDoctorDegree.getInstitution());
                    doctorDegree.setMedicalSpeciality(newDoctorDegree.getMedicalSpeciality());
                    doctorDegree.setGrade(newDoctorDegree.getGrade());
                    doctorDegree.setStartDateTime(newDoctorDegree.getStartDateTime());
                    doctorDegree.setEndDateTime(newDoctorDegree.getEndDateTime());
                    return doctorDegreeRepository.save(doctorDegree);
                })
                .orElseGet(() -> {
                    newDoctorDegree.setId(doctorDegreeId);
                    return doctorDegreeRepository.save(newDoctorDegree);
                });
    }

    @Override
    public ResponseEntity<Object> deleteDoctorDegreeById(Long doctorDegreeId) {
        log.info("Deleting doctor degree with id: {}", doctorDegreeId);
        doctorDegreeRepository.deleteById(doctorDegreeId);
        if (doctorDegreeRepository.findById(doctorDegreeId).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
        } else return ResponseEntity.ok().body("Doctor Degree is Deleted Successfully");
    }
}
