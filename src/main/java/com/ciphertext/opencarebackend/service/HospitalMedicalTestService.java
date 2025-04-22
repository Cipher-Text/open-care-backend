package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.filter.MedicalTestFilter;
import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sadman
 */
public interface HospitalMedicalTestService {
    Page<HospitalMedicalTest> getPaginatedDataWithFilters(MedicalTestFilter medicalTestFilter, Pageable pagingSort);
    HospitalMedicalTest getHospitalMedicalTestById(Long id) throws ResourceNotFoundException;
    HospitalMedicalTest createHospitalMedicalTest(HospitalMedicalTest hospitalMedicalTest);
    HospitalMedicalTest updateHospitalMedicalTest(HospitalMedicalTest newHospitalMedicalTest, Long hospitalMedicalTestId);
    ResponseEntity<Object> deleteHospitalMedicalTestById(Long hospitalMedicalTestId);
}
