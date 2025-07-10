package com.ciphertext.opencarebackend.controller;


import com.ciphertext.opencarebackend.dto.request.BloodDonationRequest;
import com.ciphertext.opencarebackend.dto.request.DoctorRequest;
import com.ciphertext.opencarebackend.dto.response.BloodDonationResponse;
import com.ciphertext.opencarebackend.dto.response.DoctorResponse;
import com.ciphertext.opencarebackend.entity.BloodDonation;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.mapper.BloodDonationMapper;
import com.ciphertext.opencarebackend.service.BloodDonationService;
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
@RequestMapping("/api/blood-donations")
@RequiredArgsConstructor
public class BloodDonationApiController {

    private final BloodDonationService bloodDonationService;
    private final BloodDonationMapper bloodDonationMapper;


    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllBloodDonorPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<BloodDonation> pageBloodDonations = bloodDonationService.getPaginatedDataWithFilters(pagingSort);
        Page<BloodDonationResponse> bloodDonationResponses = pageBloodDonations.map(bloodDonationMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("donors", bloodDonationResponses.getContent());
        response.put("currentPage", pageBloodDonations.getNumber());
        response.put("totalItems", pageBloodDonations.getTotalElements());
        response.put("totalPages", pageBloodDonations.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BloodDonationResponse>> getAllBloodDonor() {
        List<BloodDonation> bloodDonations = bloodDonationService.getAllBloodDonor();
        List<BloodDonationResponse> bloodDonationResponses = bloodDonations.stream()
                .map(bloodDonationMapper::toResponse)
                .toList();

        return ResponseEntity.ok(bloodDonationResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodDonationResponse> getBloodDonorById(@PathVariable Long id){
        BloodDonation bloodDonation = bloodDonationService.getBloodDonorById(id);
        BloodDonationResponse bloodDonationResponse = bloodDonationMapper.toResponse(bloodDonation);
        return ResponseEntity.ok(bloodDonationResponse);
    }

    @PostMapping("")
    public ResponseEntity<BloodDonationResponse> createBloodDonation(@RequestBody BloodDonationRequest bloodDonationRequest) {
        BloodDonation bloodDonation = bloodDonationMapper.toEntity(bloodDonationRequest);
        bloodDonation = bloodDonationService.createBloodDonation(bloodDonation);
        BloodDonationResponse donationResponse = bloodDonationMapper.toResponse(bloodDonation);
        return ResponseEntity.ok(donationResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodDonationResponse> updateBloodDonationById(@RequestBody BloodDonationRequest bloodDonationRequest, @PathVariable Long id){
        BloodDonation bloodDonation = bloodDonationMapper.toEntity(bloodDonationRequest);
        bloodDonation = bloodDonationService.updateBloodDonationById(bloodDonation, id);
        BloodDonationResponse donationResponse = bloodDonationMapper.toResponse(bloodDonation);
        return ResponseEntity.ok(donationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodDonationById(@PathVariable Long id){
        bloodDonationService.deleteBloodDonationById(id);
        return ResponseEntity.ok().build();
    }
}
