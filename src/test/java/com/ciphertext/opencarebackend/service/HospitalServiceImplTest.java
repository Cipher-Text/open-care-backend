package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.repository.HospitalRepository;
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
    void createHospital_ValidHospital_ReturnsHospital() {
        Hospital hospital = new Hospital();
        hospital.setName("Test Hospital");
        when(hospitalRepository.save(hospital)).thenReturn(hospital);

        Hospital result = hospitalService.createHospital(hospital);

        assertNotNull(result);
        assertEquals("Test Hospital", result.getName());
        verify(hospitalRepository, times(1)).save(hospital);
    }

}