package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.filter.InstitutionFilter;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.InstitutionRepository;
import com.ciphertext.opencarebackend.respository.specification.Filter;
import com.ciphertext.opencarebackend.respository.specification.InJoin;
import com.ciphertext.opencarebackend.service.InstitutionService;
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
public class InstitutionServiceImpl implements InstitutionService {
    private final InstitutionRepository institutionRepository;

    @Override
    public Long getInstitutionCount() {
        return institutionRepository.count();
    }

    @Override
    public List<Institution> getAllInstitutions() {
        log.info("Fetching all institutions");
        List<Institution> institutions = institutionRepository.findAll();
        log.info("Retrieved {} institutions", institutions.size());
        return institutions;
    }

    @Override
    public Page<Institution> getPaginatedDataWithFilters(InstitutionFilter institutionFilter, Pageable pagingSort) {
        log.info("Fetching institutions with filters: {}", institutionFilter);
        List<Filter> filterList = generateQueryFilters(institutionFilter);
        Specification<Institution> specification = where(null);
        if(!filterList.isEmpty()) {
            specification = where(createSpecification(filterList.removeFirst()));
            for (Filter input : filterList) {
                specification = specification.and(createSpecification(input));
            }
        }
        log.info("Fetching institutions with filters: {}", institutionFilter);
        return institutionRepository.findAll(specification, pagingSort);
    }

    @Override
    public Institution getInstitutionById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid institution ID: {}", id);
            throw new BadRequestException("Institution ID must be positive");
        }

        log.info("Fetching institution with id: {}", id);
        return institutionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Institution not found with id: {}", id);
                    return new ResourceNotFoundException("Institution not found with id: " + id);
                });
    }

    public List<Filter> generateQueryFilters(InstitutionFilter institutionFilter) {

        List<Filter> filters = new ArrayList<>();

        if (institutionFilter.getName() != null)
            filters.add(generateIndividualFilter("name", LIKE, institutionFilter.getName()));

        if (institutionFilter.getBnName() != null)
            filters.add(generateIndividualFilter("bnName", LIKE, institutionFilter.getBnName()));

        if (institutionFilter.getEnroll() != null)
            filters.add(generateIndividualFilter("enroll", EQUALS, institutionFilter.getEnroll()));

        if (institutionFilter.getDistrictIds() != null && !institutionFilter.getDistrictIds().isEmpty()) {
            InJoin<Integer> inJoin = new InJoin<>("id", "district", "id",
                    institutionFilter.getDistrictIds());
            filters.add(generateJoinTableInFilter(inJoin, IN_JOIN));
        }

        if (institutionFilter.getHospitalTypes() != null && !institutionFilter.getHospitalTypes().isEmpty()) {
            List<HospitalType> hospitalTypes = new ArrayList<>();
            for (String hospitalType : institutionFilter.getHospitalTypes()) {
                hospitalTypes.add(HospitalType.valueOf(hospitalType));
            }
            filters.add(generateInFilter("institutionType", IN, hospitalTypes));
        }

        if (institutionFilter.getOrganizationType() != null)
            filters.add(generateIndividualFilter("organizationType", EQUALS, OrganizationType.valueOf(institutionFilter.getOrganizationType())));

        return filters;
    }
}
