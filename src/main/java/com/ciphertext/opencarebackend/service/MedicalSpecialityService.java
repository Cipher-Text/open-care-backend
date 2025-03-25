package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.entity.MedicalSpeciality;

import java.util.List;

/**
 * @author Sadman
 */
public interface MedicalSpecialityService {
    List<MedicalSpeciality> getAllSpecialities();
    MedicalSpeciality getSpecialityById(int id) throws ResourceNotFoundException;
}
