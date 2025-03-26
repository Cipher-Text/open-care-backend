package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.*;
import com.ciphertext.opencarebackend.entity.*;
import com.ciphertext.opencarebackend.entity.Division;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.DistrictMapper;
import com.ciphertext.opencarebackend.mapper.DivisionMapper;
import com.ciphertext.opencarebackend.mapper.UnionMapper;
import com.ciphertext.opencarebackend.mapper.UpazilaMapper;
import com.ciphertext.opencarebackend.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sadman
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LocationApiController {

    private final LocationService service;
    private final DivisionMapper divisionMapper;
    private final DistrictMapper districtMapper;
    private final UpazilaMapper upazilaMapper;
    private final UnionMapper unionMapper;

    @GetMapping("/divisions")
    public ResponseEntity<List<DivisionResponse>> getAllDivisions() {
        log.info("Retrieving all divisions");

        List<Division> divisions = service.getAllDivisions();
        List<DivisionResponse> divisionResponses = divisions.stream()
                .map(divisionMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(divisionResponses);
    }

    @GetMapping("/divisions/{id}")
    public ResponseEntity<DivisionResponse> getDivisionById(@PathVariable(value = "id") int divisionId)
            throws ResourceNotFoundException {
        log.info("Retrieving division with ID: {}", divisionId);

        Division division = service.getDivisionById(divisionId);
        DivisionResponse divisionResponse = divisionMapper.toResponse(division);

        return ResponseEntity.ok(divisionResponse);
    }

    @GetMapping("/divisions/{id}/districts")
    public ResponseEntity<List<DistrictResponse>> getAllDistrictsByDivisionId(@PathVariable(value = "id") int divisionId) throws ResourceNotFoundException {
        log.info("Retrieving all districts by division ID: {}", divisionId);
        List<District> districts = service.getAllDistrictsByDivisionId(divisionId);
        List<DistrictResponse> districtResponses = districts.stream()
                .map(districtMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(districtResponses);
    }

    @GetMapping("/districts")
    public ResponseEntity<List<DistrictResponse>> getAllDistricts() {
        log.info("Retrieving all districts");
        List<District> districts = service.getAllDistricts();
        List<DistrictResponse> districtResponses = districts.stream()
                .map(districtMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(districtResponses);
    }

    @GetMapping("/districts/{id}")
    public ResponseEntity<DistrictResponse> getDistrictById(@PathVariable(value = "id") int districtId)
            throws ResourceNotFoundException {
        log.info("Retrieving division with ID: {}", districtId);

        District district = service.getDistrictById(districtId);
        DistrictResponse districtResponse = districtMapper.toResponse(district);

        return ResponseEntity.ok(districtResponse);
    }

    @GetMapping("/districts/{id}/upazilas")
    public ResponseEntity<List<UpazilaResponse>> getAllUpazilasByUpazilaId(@PathVariable(value = "id") int districtId) throws ResourceNotFoundException {
        log.info("Retrieving all upazilas by district ID: {}", districtId);
        List<Upazila> upazilas = service.getAllUpazilasByDistrictId(districtId);
        List<UpazilaResponse> upazilaResponses = upazilas.stream()
                .map(upazilaMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(upazilaResponses);
    }

    @GetMapping("/upazilas")
    public ResponseEntity<List<UpazilaResponse>> getAllUpazilas() {
        log.info("Retrieving all upazilas");
        List<Upazila> upazilas = service.getAllUpazilas();
        List<UpazilaResponse> upazilaResponses = upazilas.stream()
                .map(upazilaMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(upazilaResponses);
    }

    @GetMapping("/upazilas/{id}")
    public ResponseEntity<UpazilaResponse> getUpazilaById(@PathVariable(value = "id") int upazilaId)
            throws ResourceNotFoundException {
        log.info("Retrieving upazila with ID: {}", upazilaId);

        Upazila upazila = service.getUpazilaById(upazilaId);
        UpazilaResponse upazilaResponse = upazilaMapper.toResponse(upazila);

        return ResponseEntity.ok(upazilaResponse);
    }

    @GetMapping("/upazilas/{id}/unions")
    public ResponseEntity<List<UnionResponse>> getAllUnionByUpazilaId(@PathVariable(value = "id") int upazilaId) throws ResourceNotFoundException {
        log.info("Retrieving all unions by upazila ID: {}", upazilaId);
        List<Union> unions = service.getAllUnionsByUpazilaId(upazilaId);
        List<UnionResponse> unionResponses = unions.stream()
                .map(unionMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(unionResponses);
    }
}
