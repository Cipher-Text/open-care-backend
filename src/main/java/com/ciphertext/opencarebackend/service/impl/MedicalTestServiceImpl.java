package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.filter.MedicalTestFilter;
import com.ciphertext.opencarebackend.entity.MedicalTest;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.MedicalTestRepository;
import com.ciphertext.opencarebackend.respository.specification.Filter;
import com.ciphertext.opencarebackend.service.MedicalTestService;
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

import static com.ciphertext.opencarebackend.respository.specification.QueryFilterUtils.generateIndividualFilter;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.EQUALS;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.LIKE;
import static com.ciphertext.opencarebackend.respository.specification.SpecificationBuilder.createSpecification;
import static org.springframework.data.jpa.domain.Specification.where;

/**
* Implementation of MedicalTestService for managing medical tests
*/

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicalTestServiceImpl implements MedicalTestService {
    
    private final MedicalTestRepository medicalTestRepository;

    @Override
    public List<MedicalTest> getAllMedicalTests() {
        log.info("Fetching all medicalTests");
        List<MedicalTest> medicalTests = medicalTestRepository.findAll();
        log.info("Retrieved {} medicalTests", medicalTests.size());
        return medicalTests;
    }

    @Override
    public Page<MedicalTest> getPaginatedDataWithFilters(MedicalTestFilter medicalTestFilter, Pageable pagingSort) {
        log.info("Fetching medicalTests with filters: {}", medicalTestFilter);
        List<Filter> filterList = generateQueryFilters(medicalTestFilter);
        Specification<MedicalTest> specification = where(null);
        if(!filterList.isEmpty()) {
            specification = where(createSpecification(filterList.removeFirst()));
            for (Filter input : filterList) {
                specification = specification.and(createSpecification(input));
            }
        }
        log.info("Fetching medicalTests with filters: {}", medicalTestFilter);
        return medicalTestRepository.findAll(specification, pagingSort);
    }

    @Override
    public MedicalTest getMedicalTestById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid medicalTest ID: {}", id);
            throw new BadRequestException("MedicalTest ID must be positive");
        }

        log.info("Fetching medicalTest with id: {}", id);
        return medicalTestRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("MedicalTest not found with id: {}", id);
                    return new ResourceNotFoundException("MedicalTest not found with id: " + id);
                });
    }

    @Override
    public MedicalTest createMedicalTest(MedicalTest medicalTest) {
        log.info("Creating medicalTest: {}", medicalTest);
        return medicalTestRepository.save(medicalTest);
    }

    @Override
    public MedicalTest updateMedicalTest(MedicalTest newMedicalTest, int medicalTestId) {
        log.info("Updating medicalTest: {}", newMedicalTest);
        return medicalTestRepository.findById(medicalTestId)
                .map(medicalTest -> {
                    medicalTest.setName(newMedicalTest.getName());
                    medicalTest.setBnName(newMedicalTest.getBnName());
                    medicalTest.setAlternativeNames(newMedicalTest.getAlternativeNames());
                    medicalTest.setDescription(newMedicalTest.getDescription());
                    return medicalTestRepository.save(medicalTest);
                })
                .orElseGet(() -> {
                    newMedicalTest.setId(medicalTestId);
                    return medicalTestRepository.save(newMedicalTest);
                });
    }

    @Override
    public ResponseEntity<Object> deleteMedicalTestById(int medicalTestId) {
        log.info("Deleting medicalTest with id: {}", medicalTestId);
        medicalTestRepository.deleteById(medicalTestId);
        if (medicalTestRepository.findById(medicalTestId).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
        } else return ResponseEntity.ok().body("MedicalTest is Deleted Successfully");
    }

    public List<Filter> generateQueryFilters(MedicalTestFilter medicalTestFilter) {

        List<Filter> filters = new ArrayList<>();

        if (medicalTestFilter.getName() != null)
            filters.add(generateIndividualFilter("name", LIKE, medicalTestFilter.getName()));

        if(medicalTestFilter.getParentMedicalTestId() != null) {
            filters.add(generateIndividualFilter("parentId",  EQUALS, medicalTestFilter.getParentMedicalTestId()));
        }

        return filters;
    }
}
