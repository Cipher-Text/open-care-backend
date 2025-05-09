package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.response.MedicalSpecialityResponse;
import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author Sadman
 */
public interface DoctorWorkplaceService {
    List<DoctorWorkplace> getDoctorWorkplacesByDoctorId(Long doctorId);
    DoctorWorkplace getDoctorWorkplaceById(Long id) throws ResourceNotFoundException;
    DoctorWorkplace createDoctorWorkplace(DoctorWorkplace doctorWorkplace);
    DoctorWorkplace updateDoctorWorkplace(DoctorWorkplace newDoctorWorkplace, Long doctorWorkplaceId);
    ResponseEntity<Object> deleteDoctorWorkplaceById(Long doctorWorkplaceId);
    List<MedicalSpecialityResponse> getTopMedicalSpecialities(Integer limit);
}
