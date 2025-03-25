package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.response.DegreeResponse;
import com.ciphertext.opencarebackend.entity.Degree;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.DegreeMapper;
import com.ciphertext.opencarebackend.service.DegreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/degrees")
@RequiredArgsConstructor
public class DegreeApiController {
    private final DegreeService degreeService;
    private final DegreeMapper degreeMapper;

    @GetMapping
    public ResponseEntity<List<DegreeResponse>> getAllDegrees() {
        log.info("Retrieving all degrees");

        List<Degree> degrees = degreeService.getAllDegrees();
        List<DegreeResponse> degreeResponses = degrees.stream()
                .map(degreeMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(degreeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DegreeResponse> getDegreeById(@PathVariable int id)
            throws ResourceNotFoundException {
        log.info("Retrieving degree with ID: {}", id);

        Degree degree = degreeService.getDegreeById(id);
        DegreeResponse degreeResponse = degreeMapper.toResponse(degree);

        return ResponseEntity.ok(degreeResponse);
    }
}
