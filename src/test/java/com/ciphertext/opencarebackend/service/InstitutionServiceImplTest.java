package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Institution;

import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.repository.InstitutionRepository;
import com.ciphertext.opencarebackend.service.impl.InstitutionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstitutionServiceImplTest {

    @Mock
    private InstitutionRepository institutionRepository;

    @InjectMocks
    private InstitutionServiceImpl institutionService;

    @Test
    void getInstitutionCount_ReturnsCount() {
        when(institutionRepository.count()).thenReturn(5L);

        Long result = institutionService.getInstitutionCount();

        assertEquals(5L, result);
        verify(institutionRepository, times(1)).count();
    }

    @Test
    void getAllInstitutions_ReturnsList() {
        List<Institution> institutions = List.of(new Institution(), new Institution());
        when(institutionRepository.findAll()).thenReturn(institutions);

        List<Institution> result = institutionService.getAllInstitutions();

        assertEquals(2, result.size());
        verify(institutionRepository, times(1)).findAll();
    }

    @Test
    void getInstitutionById_ValidId_ReturnsInstitution() {
        Institution institution = new Institution();
        institution.setId(1);
        when(institutionRepository.findById(1)).thenReturn(Optional.of(institution));

        Institution result = institutionService.getInstitutionById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(institutionRepository, times(1)).findById(1);
    }

    @Test
    void getInstitutionById_InvalidId_ThrowsBadRequest() {
        assertThrows(BadRequestException.class, () -> institutionService.getInstitutionById(0));
        verify(institutionRepository, never()).findById(anyInt());
    }

    @Test
    void getInstitutionById_NotFound_ThrowsResourceNotFound() {
        when(institutionRepository.findById(10)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> institutionService.getInstitutionById(10));
        verify(institutionRepository).findById(10);
    }

//    @Test
//    void getPaginatedDataWithFilters_EmptyFilter_ReturnsPage() {
//        InstitutionFilter filter = new InstitutionFilter();
//        Pageable pageable = PageRequest.of(0, 10);
//        Page<Institution> page = new PageImpl<>(List.of(new Institution()));
//
//        when(institutionRepository.findAll(any(), eq(pageable))).thenReturn(page);
//
//        Page<Institution> result = institutionService.getPaginatedDataWithFilters(filter, pageable);
//
//        assertEquals(1, result.getContent().size());
//        verify(institutionRepository).findAll(any(), eq(pageable));
//    }
//
//    @Test
//    void generateQueryFilters_AllFieldsProvided_ReturnsFilterList() {
//        InstitutionFilter filter = new InstitutionFilter();
//        filter.setName("Test Name");
//        filter.setBnName("টেস্ট নাম");
//        filter.setEnroll(true);
//        filter.setDistrictIds(List.of(1, 2));
//        filter.setHospitalTypes(List.of("GENERAL", "SPECIALIZED"));
//        filter.setOrganizationType("GOVERNMENT");
//
//        List<?> filters = institutionService.generateQueryFilters(filter);
//
//        assertFalse(filters.isEmpty());
//        assertEquals(6, filters.size());
//    }
//
//    @Test
//    void generateQueryFilters_EmptyFilter_ReturnsEmptyList() {
//        InstitutionFilter filter = new InstitutionFilter();
//
//        List<?> filters = institutionService.generateQueryFilters(filter);
//
//        assertTrue(filters.isEmpty());
//    }
}
