package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.filter.HospitalFilter;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.entity.Hospital;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sadman
 */
public interface HospitalService {
    Long getHospitalCount();
    List<Hospital> getAllHospitals();
    Page<Hospital> getPaginatedDataWithFilters(HospitalFilter hospitalFilter, Pageable pagingSort);
    Hospital getHospitalById(int id) throws ResourceNotFoundException;
    Hospital createHospital(Hospital hospital);
    Hospital updateHospital(Hospital newHospital, int hospitalId);
    ResponseEntity<Object> deleteHospitalById(int hospitalId);
}
