package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.filter.MedicalTestFilter;
import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.repository.HospitalMedicalTestRepository;
import com.ciphertext.opencarebackend.repository.specification.Filter;
import com.ciphertext.opencarebackend.service.HospitalMedicalTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ciphertext.opencarebackend.repository.specification.QueryFilterUtils.generateIndividualFilter;
import static com.ciphertext.opencarebackend.repository.specification.QueryFilterUtils.generateJoinTableFilter;
import static com.ciphertext.opencarebackend.repository.specification.QueryOperator.*;
import static com.ciphertext.opencarebackend.repository.specification.SpecificationBuilder.createSpecification;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class HospitalMedicalTestServiceImpl implements HospitalMedicalTestService {
    private final HospitalMedicalTestRepository hospitalMedicalTestRepository;

    @Override
    public Page<HospitalMedicalTest> getPaginatedDataWithFilters(MedicalTestFilter medicalTestFilter, Pageable pagingSort) {
        log.info("Fetching hospitals with filters: {}", medicalTestFilter);
        List<Filter> filterList = generateQueryFilters(medicalTestFilter);
        Specification<HospitalMedicalTest> specification = where(null);
        if(!filterList.isEmpty()) {
            specification = where(createSpecification(filterList.removeFirst()));
            for (Filter input : filterList) {
                specification = specification.and(createSpecification(input));
            }
        }
        log.info("Fetching hospitals with filters: {}", medicalTestFilter);
        return hospitalMedicalTestRepository.findAll(specification, pagingSort);
    }

    @Override
    public HospitalMedicalTest getHospitalMedicalTestById(Long id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid doctor workplace ID: {}", id);
            throw new BadRequestException("Doctor Workplace ID must be positive");
        }

        log.info("Fetching doctor workplace with id: {}", id);
        return hospitalMedicalTestRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Doctor Workplace not found with id: {}", id);
                    return new ResourceNotFoundException("Doctor Workplace not found with id: " + id);
                });
    }

    @Override
    public HospitalMedicalTest createHospitalMedicalTest(HospitalMedicalTest hospitalMedicalTest) {
        log.info("Creating doctor workplace: {}", hospitalMedicalTest);
        return hospitalMedicalTestRepository.save(hospitalMedicalTest);
    }

    @Override
    public HospitalMedicalTest updateHospitalMedicalTest(HospitalMedicalTest newHospitalMedicalTest, Long hospitalMedicalTestId) {
        log.info("Updating doctor workplace: {}", newHospitalMedicalTest);
        return hospitalMedicalTestRepository.findById(hospitalMedicalTestId)
                .map(hospitalMedicalTest -> {
                    hospitalMedicalTest.setHospital(hospitalMedicalTest.getHospital());
                    hospitalMedicalTest.setMedicalTest(hospitalMedicalTest.getMedicalTest());
                    hospitalMedicalTest.setPrice(hospitalMedicalTest.getPrice());
                    hospitalMedicalTest.setIsActive(hospitalMedicalTest.getIsActive());
                    return hospitalMedicalTestRepository.save(hospitalMedicalTest);
                })
                .orElseGet(() -> {
                    newHospitalMedicalTest.setId(hospitalMedicalTestId);
                    return hospitalMedicalTestRepository.save(newHospitalMedicalTest);
                });
    }

    @Override
    public ResponseEntity<Object> deleteHospitalMedicalTestById(Long hospitalMedicalTestId) {
        log.info("Deleting doctor workplace with id: {}", hospitalMedicalTestId);
        hospitalMedicalTestRepository.deleteById(hospitalMedicalTestId);
        if (hospitalMedicalTestRepository.findById(hospitalMedicalTestId).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
        } else return ResponseEntity.ok().body("Doctor Workplace is Deleted Successfully");
    }

    public List<Filter> generateQueryFilters(MedicalTestFilter medicalTestFilter) {

        List<Filter> filters = new ArrayList<>();

        if(medicalTestFilter.getName() != null && !medicalTestFilter.getName().isEmpty()) {
            filters.add(generateIndividualFilter("name", LIKE, medicalTestFilter.getName()));
        }

        if(medicalTestFilter.getHospitalId() != null) {
            filters.add(generateJoinTableFilter("id", "hospital", JOIN, medicalTestFilter.getHospitalId()));
        }

        if(medicalTestFilter.getMedicalTestId() != null) {
            filters.add(generateJoinTableFilter("id", "medicalTest", JOIN, medicalTestFilter.getMedicalTestId()));
        }

        if(medicalTestFilter.getParentMedicalTestId() != null) {
            filters.add(generateJoinTableFilter("parentId", "medicalTest", JOIN, medicalTestFilter.getParentMedicalTestId()));
        }

        if(medicalTestFilter.getMinPrice() != null) {
            filters.add(generateIndividualFilter("price", GREATER_THAN_EQUALS, medicalTestFilter.getMinPrice()));
        }

        if(medicalTestFilter.getMaxPrice() != null) {
            filters.add(generateIndividualFilter("price", LESS_THAN_EQUALS, medicalTestFilter.getMaxPrice()));
        }

        return filters;
    }
}
