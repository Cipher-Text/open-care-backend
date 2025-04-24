package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.Medicine;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.MedicineRepository;
import com.ciphertext.opencarebackend.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MedicineServiceImpl implements MedicineService {

     private final MedicineRepository medicineRepository;

    @Override
    public Page<Medicine> basicSearch(String term, Pageable pageable) {
        log.info("Performing basic search for term: {}", term);
        Page<Medicine> medicines = medicineRepository.basicSearch(term, pageable);
        log.info("Found {} medicines for term: {}", medicines.getTotalElements(), term);
        return medicines;
    }

    @Override
    public Page<Medicine> fullTextSearch(String term, Pageable pageable) {
        log.info("Performing full-text search for term: {}", term);
        Page<Medicine> medicines = medicineRepository.fullTextSearch(term, pageable);
        log.info("Found {} medicines for term: {}", medicines.getTotalElements(), term);
        return medicines;
    }

    @Override
    public Page<Medicine> advancedSearch(String brandName, String generic, String manufacturer, String type, Pageable pageable) {
        log.info("Performing advanced search with filters: brandName={}, generic={}, manufacturer={}, type={}", brandName, generic, manufacturer, type);
        Page<Medicine> medicines = medicineRepository.advancedSearch(brandName, generic, manufacturer, type, pageable);
        log.info("Found {} medicines for advanced search", medicines.getTotalElements());
        return medicines;
    }

    @Override
    public Medicine getMedicineById(int id) throws ResourceNotFoundException {
        log.info("Fetching medicine with ID: {}", id);
        return medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with ID: " + id));
    }
}
