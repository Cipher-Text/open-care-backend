package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.filter.MedicalTestFilter;
import com.ciphertext.opencarebackend.entity.MedicalTest;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.entity.MedicalTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MedicalTestService {
    List<MedicalTest> getAllMedicalTests();
    Page<MedicalTest> getPaginatedDataWithFilters(MedicalTestFilter medicalTestFilter, Pageable pagingSort);
    MedicalTest getMedicalTestById(int id) throws ResourceNotFoundException;
    MedicalTest createMedicalTest(MedicalTest medicalTest);
    MedicalTest updateMedicalTest(MedicalTest newMedicalTest, int medicalTestId);
    ResponseEntity<Object> deleteMedicalTestById(int medicalTestId);
}
