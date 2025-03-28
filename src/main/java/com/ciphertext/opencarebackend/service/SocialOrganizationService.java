package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.dto.filter.SocialOrganizationFilter;
import com.ciphertext.opencarebackend.entity.SocialOrganization;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Sadman
 */
public interface SocialOrganizationService {
    List<SocialOrganization> getAllSocialOrganizations();
    SocialOrganization getSocialOrganizationById(int id) throws ResourceNotFoundException;
    Page<SocialOrganization> getPaginatedDataWithFilters(SocialOrganizationFilter institutionFilter, Pageable pagingSort);
}
