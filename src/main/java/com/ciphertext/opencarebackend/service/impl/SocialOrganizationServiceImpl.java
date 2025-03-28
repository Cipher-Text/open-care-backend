package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.filter.SocialOrganizationFilter;
import com.ciphertext.opencarebackend.entity.SocialOrganization;
import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import com.ciphertext.opencarebackend.enums.SocialOrganizationType;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.SocialOrganizationRepository;
import com.ciphertext.opencarebackend.respository.specification.Filter;
import com.ciphertext.opencarebackend.respository.specification.InJoin;
import com.ciphertext.opencarebackend.service.SocialOrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ciphertext.opencarebackend.respository.specification.QueryFilterUtils.*;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.*;
import static com.ciphertext.opencarebackend.respository.specification.SpecificationBuilder.createSpecification;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SocialOrganizationServiceImpl implements SocialOrganizationService {
    private final SocialOrganizationRepository socialOrganizationRepository;

    @Override
    public List<SocialOrganization> getAllSocialOrganizations() {
        log.info("Fetching all social organizations");
        List<SocialOrganization> socialOrganizations = socialOrganizationRepository.findAll();
        log.info("Retrieved {} social organizations", socialOrganizations.size());
        return socialOrganizations;
    }

    @Override
    public Page<SocialOrganization> getPaginatedDataWithFilters(SocialOrganizationFilter socialOrganizationFilter, Pageable pagingSort) {
        log.info("Fetching social organizations with filters: {}", socialOrganizationFilter);
        List<Filter> filterList = generateQueryFilters(socialOrganizationFilter);
        Specification<SocialOrganization> specification = where(null);
        if(!filterList.isEmpty()) {
            specification = where(createSpecification(filterList.removeFirst()));
            for (Filter input : filterList) {
                specification = specification.and(createSpecification(input));
            }
        }
        log.info("Fetching social organizations with filters: {}", socialOrganizationFilter);
        return socialOrganizationRepository.findAll(specification, pagingSort);
    }

    @Override
    public SocialOrganization getSocialOrganizationById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid social organization ID: {}", id);
            throw new BadRequestException("Social Organization ID must be positive");
        }

        log.info("Fetching socialOrganization with id: {}", id);
        return socialOrganizationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Social Organization not found with id: {}", id);
                    return new ResourceNotFoundException("Social Organization not found with id: " + id);
                });
    }

    public List<Filter> generateQueryFilters(SocialOrganizationFilter socialOrganizationFilter) {

        List<Filter> filters = new ArrayList<>();

        if (socialOrganizationFilter.getName() != null)
            filters.add(generateIndividualFilter("name", LIKE, socialOrganizationFilter.getName()));

        if (socialOrganizationFilter.getBnName() != null)
            filters.add(generateIndividualFilter("bnName", LIKE, socialOrganizationFilter.getBnName()));

        if (socialOrganizationFilter.getEmail() != null)
            filters.add(generateIndividualFilter("email", LIKE, socialOrganizationFilter.getEmail()));

        if (socialOrganizationFilter.getPhone() != null)
            filters.add(generateIndividualFilter("phone", LIKE, socialOrganizationFilter.getPhone()));

        if (socialOrganizationFilter.getSocialOrganizationType() != null)
            filters.add(generateIndividualFilter("socialOrganizationType", EQUALS, SocialOrganizationType.valueOf(socialOrganizationFilter.getSocialOrganizationType())));

        return filters;
    }
}
