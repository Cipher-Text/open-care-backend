package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.MedicineResponse;
import com.ciphertext.opencarebackend.entity.Medicine;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.MedicineMapper;
import com.ciphertext.opencarebackend.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineApiController {

    private final MedicineService medicineService;
    private final MedicineMapper medicineMapper;

    @GetMapping("/basic")
    public ResponseEntity<Map<String, Object>> getBasicMedicinesPage(
            @RequestParam(required = false) String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);

        Page<Medicine> pageMedicines = medicineService.basicSearch(term, pagingSort);

        Page<MedicineResponse> pageMedicineResponses = pageMedicines.map(medicineMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("medicines", pageMedicineResponses.getContent());
        response.put("currentPage", pageMedicines.getNumber());
        response.put("totalItems", pageMedicines.getTotalElements());
        response.put("totalPages", pageMedicines.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/full-text")
    public ResponseEntity<Map<String, Object>> getFullTextMedicinesPage(
            @RequestParam(required = false) String term,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);

        Page<Medicine> pageMedicines = medicineService.fullTextSearch(term, pagingSort);

        Page<MedicineResponse> pageMedicineResponses = pageMedicines.map(medicineMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("medicines", pageMedicineResponses.getContent());
        response.put("currentPage", pageMedicines.getNumber());
        response.put("totalItems", pageMedicines.getTotalElements());
        response.put("totalPages", pageMedicines.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/advanced")
    public ResponseEntity<Map<String, Object>> getAdvancedMedicinesPage(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String generic,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size);

        Page<Medicine> pageMedicines =
                medicineService.advancedSearch(brandName, generic, manufacturer, type, pagingSort);

        Page<MedicineResponse> pageMedicineResponses = pageMedicines.map(medicineMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("medicines", pageMedicineResponses.getContent());
        response.put("currentPage", pageMedicines.getNumber());
        response.put("totalItems", pageMedicines.getTotalElements());
        response.put("totalPages", pageMedicines.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicineResponse> getMedicineById(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Retrieving medicine with ID: {}", id);

        Medicine medicine = medicineService.getMedicineById(id);
        MedicineResponse medicineResponse = medicineMapper.toResponse(medicine);

        return ResponseEntity.ok(medicineResponse);
    }
}
