package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sadman
 */
public interface HospitalMedicalTestService {
    List<HospitalMedicalTest> getHospitalMedicalTestsByDoctorId(Long hospitalId);
    HospitalMedicalTest getHospitalMedicalTestById(Long id) throws ResourceNotFoundException;
    HospitalMedicalTest createHospitalMedicalTest(HospitalMedicalTest hospitalMedicalTest);
    HospitalMedicalTest updateHospitalMedicalTest(HospitalMedicalTest newHospitalMedicalTest, Long hospitalMedicalTestId);
    ResponseEntity<Object> deleteHospitalMedicalTestById(Long hospitalMedicalTestId);
}
