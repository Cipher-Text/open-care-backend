package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.MedicalSpeciality;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.repository.MedicalSpecialityRepository;
import com.ciphertext.opencarebackend.service.MedicalSpecialityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of MedicalSpecialityService for managing medical specialities for doctors
 */

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicalSpecialityServiceImpl implements MedicalSpecialityService {
    
    private final MedicalSpecialityRepository medicalSpecialityRepository;

    @Override
    public List<MedicalSpeciality> getAllSpecialities() {
        log.info("Fetching all medical specialities");
        List<MedicalSpeciality> medicalSpecialities = medicalSpecialityRepository.findAll();
        log.info("Retrieved {} medical specialities", medicalSpecialities.size());
        return medicalSpecialities;
    }

    @Override
    public MedicalSpeciality getSpecialityById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid medical speciality ID: {}", id);
            throw new BadRequestException("Medical Speciality ID must be positive");
        }

        log.info("Fetching medical speciality with id: {}", id);
        return medicalSpecialityRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Medical Speciality not found with id: {}", id);
                    return new ResourceNotFoundException("Medical Speciality not found with id: " + id);
                });
    }
}
