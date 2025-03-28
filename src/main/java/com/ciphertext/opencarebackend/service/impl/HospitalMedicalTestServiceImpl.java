package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.HospitalMedicalTestRepository;
import com.ciphertext.opencarebackend.service.HospitalMedicalTestService;
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
public class HospitalMedicalTestServiceImpl implements HospitalMedicalTestService {
    private final HospitalMedicalTestRepository hospitalMedicalTestRepository;

    @Override
    public List<HospitalMedicalTest> getHospitalMedicalTestsByDoctorId(Long hospitalId) {
        log.info("Fetching all doctor workplaces");
        List<HospitalMedicalTest> hospitalMedicalTests = hospitalMedicalTestRepository.findAll();
        log.info("Retrieved {} doctor workplaces", hospitalMedicalTests.size());
        return hospitalMedicalTests;
    }

    @Override
    public HospitalMedicalTest getHospitalMedicalTestById(Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid doctor workplace ID: {}", id);
            throw new BadRequestException("Doctor Workplace ID must be positive");
        }

        log.info("Fetching doctor workplace with id: {}", id);
        return hospitalMedicalTestRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor Workplace not found with id: {}", id);
                    return new ResourceNotFoundException("Doctor Workplace not found with id: " + id);
                });
    }

    @Override
    public HospitalMedicalTest createHospitalMedicalTest(HospitalMedicalTest hospitalMedicalTest) {
        log.info("Creating doctor workplace: {}", hospitalMedicalTest);
        return hospitalMedicalTestRepository.save(hospitalMedicalTest);
    }

    @Override
    public HospitalMedicalTest updateHospitalMedicalTest(HospitalMedicalTest newHospitalMedicalTest, Long hospitalMedicalTestId) {
        log.info("Updating doctor workplace: {}", newHospitalMedicalTest);
        return hospitalMedicalTestRepository.findById(hospitalMedicalTestId)
                .map(hospitalMedicalTest -> {
                    hospitalMedicalTest.setHospital(hospitalMedicalTest.getHospital());
                    hospitalMedicalTest.setMedicalTest(hospitalMedicalTest.getMedicalTest());
                    hospitalMedicalTest.setPrice(hospitalMedicalTest.getPrice());
                    hospitalMedicalTest.setIsActive(hospitalMedicalTest.getIsActive());
                    return hospitalMedicalTestRepository.save(hospitalMedicalTest);
                })
                .orElseGet(() -> {
                    newHospitalMedicalTest.setId(hospitalMedicalTestId);
                    return hospitalMedicalTestRepository.save(newHospitalMedicalTest);
                });
    }

    @Override
    public ResponseEntity<Object> deleteHospitalMedicalTestById(Long hospitalMedicalTestId) {
        log.info("Deleting doctor workplace with id: {}", hospitalMedicalTestId);
        hospitalMedicalTestRepository.deleteById(hospitalMedicalTestId);
        if (hospitalMedicalTestRepository.findById(hospitalMedicalTestId).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
        } else return ResponseEntity.ok().body("Doctor Workplace is Deleted Successfully");
    }
}
