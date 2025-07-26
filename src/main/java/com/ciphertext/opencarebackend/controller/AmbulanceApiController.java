package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.AmbulanceRequest;
import com.ciphertext.opencarebackend.dto.response.AmbulanceResponse;
import com.ciphertext.opencarebackend.entity.Ambulance;
import com.ciphertext.opencarebackend.mapper.AmbulanceMapper;
import com.ciphertext.opencarebackend.service.AmbulanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/ambulances")
@RequiredArgsConstructor
public class AmbulanceApiController {

    private final AmbulanceService ambulanceService;
    private final AmbulanceMapper ambulanceMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllAmbulancesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Ambulance> pageAds = ambulanceService.getPaginatedDataWithFilters(pagingSort);
        Page<AmbulanceResponse> adResponses = pageAds.map(ambulanceMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("ambulances", adResponses.getContent());
        response.put("currentPage", pageAds.getNumber());
        response.put("totalItems", pageAds.getTotalElements());
        response.put("totalPages", pageAds.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AmbulanceResponse>> getAllAmbulances() {
        List<Ambulance> ambulances = ambulanceService.getAllAmbulance();
        List<AmbulanceResponse> amResponses = ambulances.stream()
                .map(ambulanceMapper::toResponse)
                .toList();
        return ResponseEntity.ok(amResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AmbulanceResponse> getAmbulanceById(@PathVariable Long id) {
        Ambulance amb = ambulanceService.getAmbulanceById(id);
        AmbulanceResponse ambResponse = ambulanceMapper.toResponse(amb);
        return ResponseEntity.ok(ambResponse);
    }

    @PostMapping
    public ResponseEntity<AmbulanceResponse> createAmbulance(@RequestBody AmbulanceRequest ambRequest) {
        Ambulance amb = ambulanceMapper.toEntity(ambRequest);
        amb = ambulanceService.createAmbulance(amb);
        AmbulanceResponse ambResponse = ambulanceMapper.toResponse(amb);
        return ResponseEntity.ok(ambResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AmbulanceResponse> updateAmbulanceById(@RequestBody AmbulanceRequest ambRequest, @PathVariable Long id) {
        Ambulance amb = ambulanceMapper.toEntity(ambRequest);
        amb = ambulanceService.updateAmbulanceById(amb, id);
        AmbulanceResponse ambResponse = ambulanceMapper.toResponse(amb);
        return ResponseEntity.ok(ambResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmbulanceById(@PathVariable Long id) {
        ambulanceService.deleteAmbulanceById(id);
        return ResponseEntity.ok().build();
    }
}
