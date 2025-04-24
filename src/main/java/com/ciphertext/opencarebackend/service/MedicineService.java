package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Medicine;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MedicineService {
    Page<Medicine> basicSearch(String term, Pageable pageable);
    Page<Medicine> fullTextSearch(String term, Pageable pageable);
    Page<Medicine> advancedSearch(String brandName, String generic, String manufacturer, String type, Pageable pageable);
    Medicine getMedicineById(int id) throws ResourceNotFoundException;
}
