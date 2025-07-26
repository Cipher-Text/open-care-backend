package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.District;
import com.ciphertext.opencarebackend.entity.Division;
import com.ciphertext.opencarebackend.entity.Union;
import com.ciphertext.opencarebackend.entity.Upazila;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.repository.DistrictRepository;
import com.ciphertext.opencarebackend.repository.DivisionRepository;
import com.ciphertext.opencarebackend.repository.UnionRepository;
import com.ciphertext.opencarebackend.repository.UpazilaRepository;
import com.ciphertext.opencarebackend.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of LocationService for managing geographic locations
 * including divisions, districts, upazilas, and unions
 */
@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;
    private final UnionRepository unionRepository;

    @Override
    public List<Division> getAllDivisions() {
        log.info("Fetching all divisions");
        List<Division> divisions = divisionRepository.findAll();
        log.info("Retrieved {} divisions", divisions.size());
        return divisions;
    }

    @Override
    public Division getDivisionById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid division ID: {}", id);
            throw new BadRequestException("Division ID must be positive");
        }

        log.info("Fetching division with id: {}", id);
        return divisionRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Division not found with id: {}", id);
                    return new ResourceNotFoundException("Division not found with id: " + id);
                });
    }

    @Override
    public List<District> getAllDistricts() {
        log.info("Fetching all districts");
        List<District> districts = districtRepository.findAll();
        log.info("Retrieved {} districts", districts.size());
        return districts;
    }

    @Override
    public District getDistrictById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid district ID: {}", id);
            throw new BadRequestException("District ID must be positive");
        }

        log.info("Fetching district with id: {}", id);
        return districtRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("District not found with id: {}", id);
                    return new ResourceNotFoundException("District not found with id: " + id);
                });
    }

    @Override
    public List<District> getAllDistrictsByDivisionId(int divisionId) throws ResourceNotFoundException {
        if (divisionId <= 0) {
            log.error("Invalid division ID: {}", divisionId);
            throw new BadRequestException("Division ID must be positive");
        }

        log.info("Fetching all districts for division id: {}", divisionId);
        Division division = getDivisionById(divisionId);
        List<District> districts = districtRepository.getAllByDivision(division);
        log.info("Retrieved {} districts for division id: {}", districts.size(), divisionId);
        return districts;
    }

    @Override
    public List<Upazila> getAllUpazilas() {
        log.info("Fetching all upazilas");
        List<Upazila> upazilas = upazilaRepository.findAll();
        log.info("Retrieved {} upazilas", upazilas.size());
        return upazilas;
    }

    @Override
    public Upazila getUpazilaById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid upazila ID: {}", id);
            throw new BadRequestException("Upazila ID must be positive");
        }

        log.info("Fetching upazila with id: {}", id);
        return upazilaRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Upazila not found with id: {}", id);
                    return new ResourceNotFoundException("Upazila not found with id: " + id);
                });
    }

    @Override
    public List<Upazila> getAllUpazilasByDistrictId(int districtId) throws ResourceNotFoundException {
        if (districtId <= 0) {
            log.error("Invalid district ID: {}", districtId);
            throw new BadRequestException("District ID must be positive");
        }

        log.info("Fetching all upazilas for district id: {}", districtId);
        District district = getDistrictById(districtId);
        List<Upazila> upazilas = upazilaRepository.getAllByDistrict(district);
        log.info("Retrieved {} upazilas for district id: {}", upazilas.size(), districtId);
        return upazilas;
    }

    @Override
    public List<Union> getAllUnionsByUpazilaId(int upazilaId) throws ResourceNotFoundException {
        if (upazilaId <= 0) {
            log.error("Invalid upazila ID: {}", upazilaId);
            throw new BadRequestException("Upazila ID must be positive");
        }

        log.info("Fetching all unions for upazila id: {}", upazilaId);
        Upazila upazila = getUpazilaById(upazilaId);
        List<Union> unions = unionRepository.getAllByUpazila(upazila);
        log.info("Retrieved {} unions for upazila id: {}", unions.size(), upazilaId);
        return unions;
    }

    /**
     * Helper method to check if a collection is empty and throw an appropriate exception
     * @param collection the collection to check
     * @param message the error message
     * @throws ResourceNotFoundException if the collection is empty
     */
    private <T> void validateResultNotEmpty(List<T> collection, String message) throws ResourceNotFoundException {
        if (collection == null || collection.isEmpty()) {
            log.warn(message);
            throw new ResourceNotFoundException(message);
        }
    }
}