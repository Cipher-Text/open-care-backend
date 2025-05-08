package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.filter.InstitutionFilter;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sadman
 */
public interface InstitutionService {
    Long getInstitutionCount();
    List<Institution> getAllInstitutions();
    Institution getInstitutionById(int id) throws ResourceNotFoundException;
    Page<Institution> getPaginatedDataWithFilters(InstitutionFilter institutionFilter, Pageable pagingSort);
}
