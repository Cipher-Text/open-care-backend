package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.dto.filter.HospitalFilter;
import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.HospitalRepository;
import com.ciphertext.opencarebackend.respository.specification.Filter;
import com.ciphertext.opencarebackend.respository.specification.InJoin;
import com.ciphertext.opencarebackend.service.HospitalService;
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

import static com.ciphertext.opencarebackend.respository.specification.QueryFilterUtils.*;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.EQUALS;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.LIKE;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.IN_JOIN;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.IN;
import static com.ciphertext.opencarebackend.respository.specification.QueryOperator.JOIN;
import static com.ciphertext.opencarebackend.respository.specification.SpecificationBuilder.createSpecification;
import static org.springframework.data.jpa.domain.Specification.where;

import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {
    private final HospitalRepository hospitalRepository;

    @Override
    public List<Hospital> getAllHospitals() {
        log.info("Fetching all hospitals");
        List<Hospital> hospitals = hospitalRepository.findAll();
        log.info("Retrieved {} hospitals", hospitals.size());
        return hospitals;
    }

    @Override
    public Page<Hospital> getPaginatedDataWithFilters(HospitalFilter hospitalFilter, Pageable pagingSort) {
        log.info("Fetching hospitals with filters: {}", hospitalFilter);
        List<Filter> filterList = generateQueryFilters(hospitalFilter);
        Specification<Hospital> specification = where(null);
        if(!filterList.isEmpty()) {
            specification = where(createSpecification(filterList.removeFirst()));
            for (Filter input : filterList) {
                specification = specification.and(createSpecification(input));
            }
        }
        log.info("Fetching hospitals with filters: {}", hospitalFilter);
        return hospitalRepository.findAll(specification, pagingSort);
    }

    @Override
    public Hospital getHospitalById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid hospital ID: {}", id);
            throw new BadRequestException("Hospital ID must be positive");
        }

        log.info("Fetching hospital with id: {}", id);
        return hospitalRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Hospital not found with id: {}", id);
                    return new ResourceNotFoundException("Hospital not found with id: " + id);
                });
    }

    @Override
    public Hospital createHospital(Hospital hospital) {
        log.info("Creating hospital: {}", hospital);
        return hospitalRepository.save(hospital);
    }

    @Override
    public Hospital updateHospital(Hospital newHospital, int hospitalId) {
        log.info("Updating hospital: {}", newHospital);
        return hospitalRepository.findById(hospitalId)
                .map(hospital -> {
                    hospital.setName(newHospital.getName());
                    hospital.setBnName(newHospital.getBnName());
                    hospital.setNumberOfBed(newHospital.getNumberOfBed());
                    hospital.setHospitalType(newHospital.getHospitalType());
                    hospital.setOrganizationType(newHospital.getOrganizationType());
                    hospital.setDistrict(newHospital.getDistrict());
                    hospital.setUpazila(newHospital.getUpazila());
                    hospital.setUnion(newHospital.getUnion());
                    return hospitalRepository.save(hospital);
                })
                .orElseGet(() -> {
                    newHospital.setId(hospitalId);
                    return hospitalRepository.save(newHospital);
                });
    }

    @Override
    public ResponseEntity<Object> deleteHospitalById(int hospitalId) {
        log.info("Deleting hospital with id: {}", hospitalId);
        hospitalRepository.deleteById(hospitalId);
        if (hospitalRepository.findById(hospitalId).isPresent()) {
            return ResponseEntity.unprocessableEntity().body("Failed to delete the specified record");
        } else return ResponseEntity.ok().body("Hospital is Deleted Successfully");
    }

    public List<Filter> generateQueryFilters(HospitalFilter hospitalFilter) {

        List<Filter> filters = new ArrayList<>();

        if (hospitalFilter.getName() != null)
            filters.add(generateIndividualFilter("name", LIKE, hospitalFilter.getName()));

        if (hospitalFilter.getBnName() != null)
            filters.add(generateIndividualFilter("bnName", LIKE, hospitalFilter.getBnName()));

        if (hospitalFilter.getNumberOfBed() != null)
            filters.add(generateIndividualFilter("numberOfBed", EQUALS, hospitalFilter.getNumberOfBed()));

        if (hospitalFilter.getDistrictIds() != null && !hospitalFilter.getDistrictIds().isEmpty()) {
            InJoin<Integer> inJoin = new InJoin<>("id", "district", "id",
                    hospitalFilter.getDistrictIds());
            filters.add(generateJoinTableInFilter(inJoin, IN_JOIN));
        }

        if (hospitalFilter.getUpazilaId() != null)
            filters.add(generateJoinTableFilter("id", "upazila", JOIN, hospitalFilter.getUpazilaId()));

        if (hospitalFilter.getUnionId() != null)
            filters.add(generateJoinTableFilter("id", "union", JOIN, hospitalFilter.getUnionId()));

        if (hospitalFilter.getHospitalTypes() != null && !hospitalFilter.getHospitalTypes().isEmpty()) {
            List<HospitalType> hospitalTypes = new ArrayList<>();
            for (String hospitalType : hospitalFilter.getHospitalTypes()) {
                hospitalTypes.add(HospitalType.valueOf(hospitalType));
            }
            filters.add(generateInFilter("hospitalType", IN, hospitalTypes));
        }

        if (hospitalFilter.getOrganizationType() != null)
            filters.add(generateIndividualFilter("organizationType", EQUALS, OrganizationType.valueOf(hospitalFilter.getOrganizationType())));

        return filters;
    }
}
