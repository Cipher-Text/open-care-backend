package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.MedicalTest;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.MedicalTestRepository;
import com.ciphertext.opencarebackend.service.MedicalTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Implementation of MedicalTestService for managing medical tests
*/

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicalTestServiceImpl implements MedicalTestService {
    
    private final MedicalTestRepository medicalTestRepository;

    @Override
    public List<MedicalTest> getAllMedicalTests() {
        log.info("Fetching all medical tests");
        List<MedicalTest> medicalTests = medicalTestRepository.findAll();
        log.info("Retrieved {} medical tests", medicalTests.size());
        return medicalTests;
    }

    @Override
    public MedicalTest getMedicalTestById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid medicalTest ID: {}", id);
            throw new BadRequestException("Medical Test ID must be positive");
        }

        log.info("Fetching medical test with id: {}", id);
        return medicalTestRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Medical Test not found with id: {}", id);
                    return new ResourceNotFoundException("Medical Test not found with id: " + id);
                });
    }
}
