package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.Degree;
import com.ciphertext.opencarebackend.entity.Degree;
import com.ciphertext.opencarebackend.exception.BadRequestException;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.DegreeRepository;
import com.ciphertext.opencarebackend.service.DegreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Implementation of DegreeService for managing degrees
*/

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DegreeServiceImpl implements DegreeService {
    
    private final DegreeRepository degreeRepository;

    @Override
    public List<Degree> getAllDegrees() {
        log.info("Fetching all degrees");
        List<Degree> degrees = degreeRepository.findAll();
        log.info("Retrieved {} degrees", degrees.size());
        return degrees;
    }

    @Override
    public Degree getDegreeById(int id) throws ResourceNotFoundException {
        if (id <= 0) {
            log.error("Invalid degree ID: {}", id);
            throw new BadRequestException("Degree ID must be positive");
        }

        log.info("Fetching degree with id: {}", id);
        return degreeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Degree not found with id: {}", id);
                    return new ResourceNotFoundException("Degree not found with id: " + id);
                });
    }
}
