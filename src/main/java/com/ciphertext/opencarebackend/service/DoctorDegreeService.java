package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.filter.DoctorFilter;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.entity.DoctorDegree;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sadman
 */
public interface DoctorDegreeService {
    List<DoctorDegree> getDoctorDegreesByDoctorId(Long doctorId);
    DoctorDegree getDoctorDegreeById(Long id) throws ResourceNotFoundException;
    DoctorDegree createDoctorDegree(DoctorDegree doctorDegree);
    DoctorDegree updateDoctorDegree(DoctorDegree newDoctorDegree, Long doctorDegreeId);
    ResponseEntity<Object> deleteDoctorDegreeById(Long doctorDegreeId);
}
