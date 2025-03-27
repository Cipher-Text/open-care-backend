package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.filter.DoctorFilter;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sadman
 */
public interface DoctorService {
    List<Doctor> getAllDoctors();
    Page<Doctor> getPaginatedDataWithFilters(DoctorFilter doctorFilter, Pageable pagingSort);
    Doctor getDoctorById(Long id) throws ResourceNotFoundException;
    Doctor createDoctor(Doctor doctor);
    Doctor updateDoctor(Doctor newDoctor, Long doctorId);
    ResponseEntity<Object> deleteDoctorById(Long doctorId);
}
