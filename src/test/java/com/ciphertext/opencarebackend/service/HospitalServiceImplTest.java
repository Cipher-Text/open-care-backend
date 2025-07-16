package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.HospitalRepository;
import com.ciphertext.opencarebackend.service.impl.HospitalServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalServiceImplTest {

    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalServiceImpl hospitalService;

    @Test
    void getHospitalById_ValidId_ReturnsHospital() {
        Hospital hospital = new Hospital();
        hospital.setId(1);
        when(hospitalRepository.findById(1)).thenReturn(Optional.of(hospital));

        Hospital result = hospitalService.getHospitalById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(hospitalRepository, times(1)).findById(1);
    }

    @Test
    void getHospitalById_InvalidId_ThrowsException() {
        when(hospitalRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hospitalService.getHospitalById(99));
        verify(hospitalRepository, times(1)).findById(99);
    }

    @Test
    void getHospitalById_NegativeId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> hospitalService.getHospitalById(-1));
        verify(hospitalRepository, never()).findById(anyInt());
    }

    // create/post tests for new hospital
    @Test
    void createHospital_ValidHospital_ReturnsHospital() {
        Hospital hospital = new Hospital();
        hospital.setName("Test Hospital");
        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        Hospital result = hospitalService.createHospital(hospital);

        assertNotNull(result);
        assertEquals("Test Hospital", result.getName());
        verify(hospitalRepository, times(1)).save(hospital);
    }

    // update/put tests for existing hospital
    @Test
    void updateHospital_ValidId_ReturnsUpdatedHospital() {
        Hospital existingHospital = new Hospital();
        existingHospital.setId(1);
        existingHospital.setName("Old Hospital");

        Hospital updatedHospital = new Hospital();
        updatedHospital.setId(1);
        updatedHospital.setName("Updated Hospital");

        when(hospitalRepository.findById(1)).thenReturn(Optional.of(existingHospital));
        when(hospitalRepository.save(updatedHospital)).thenReturn(updatedHospital);

        Hospital result = hospitalService.updateHospital(updatedHospital, 1);

        assertNotNull(result);
        assertEquals("Updated Hospital", result.getName());
        verify(hospitalRepository, times(1)).findById(1);
        verify(hospitalRepository, times(1)).save(updatedHospital);
    }

    @Test
    void updateHospital_InvalidId_ThrowsException() {
        Hospital updatedHospital = new Hospital();
        updatedHospital.setId(99);
        updatedHospital.setName("Updated Hospital");

        when(hospitalRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> hospitalService.updateHospital(updatedHospital, 99));
        verify(hospitalRepository, times(1)).findById(99);
        verify(hospitalRepository, never()).save(any());
    }


}