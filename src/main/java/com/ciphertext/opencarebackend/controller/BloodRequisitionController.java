package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.BloodDonationRequest;
import com.ciphertext.opencarebackend.dto.request.BloodRequisitionRequest;
import com.ciphertext.opencarebackend.dto.response.BloodDonationResponse;
import com.ciphertext.opencarebackend.dto.response.BloodRequisitionResponse;
import com.ciphertext.opencarebackend.entity.BloodDonation;
import com.ciphertext.opencarebackend.entity.BloodRequisition;
import com.ciphertext.opencarebackend.mapper.BloodDonationMapper;
import com.ciphertext.opencarebackend.mapper.BloodRequisitionMapper;
import com.ciphertext.opencarebackend.service.BloodDonationService;
import com.ciphertext.opencarebackend.service.BloodRequisitionService;
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
@RequestMapping("/api/blood-requisitions")
@RequiredArgsConstructor
public class BloodRequisitionController {

    private final BloodRequisitionService bloodRequisitionService;
    private final BloodRequisitionMapper bloodRequisitionMapper;


    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllBloodRequisitionPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<BloodRequisition> requisitionPages = bloodRequisitionService.getPaginatedDataWithFilters(pagingSort);
        Page<BloodRequisitionResponse> bloodRequisitionResponses = requisitionPages.map(bloodRequisitionMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("donors", bloodRequisitionResponses.getContent());
        response.put("currentPage", requisitionPages.getNumber());
        response.put("totalItems", requisitionPages.getTotalElements());
        response.put("totalPages", requisitionPages.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BloodRequisitionResponse>> getAllBloodRequisition() {
        List<BloodRequisition> bloodRequisitions = bloodRequisitionService.getAllBloodRequisition();
        List<BloodRequisitionResponse> bloodRequisitionResponses = bloodRequisitions.stream()
                .map(bloodRequisitionMapper::toResponse)
                .toList();

        return ResponseEntity.ok(bloodRequisitionResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodRequisitionResponse> getBloodRequisitionById(@PathVariable Long id){
        BloodRequisition bloodRequisition = bloodRequisitionService.getBloodRequisitionById(id);
        BloodRequisitionResponse bloodRequisitionResponse = bloodRequisitionMapper.toResponse(bloodRequisition);
        return ResponseEntity.ok(bloodRequisitionResponse);
    }

    @PostMapping("")
    public ResponseEntity<BloodRequisitionResponse> createBloodRequisition(@RequestBody BloodRequisitionRequest bloodRequisitionRequest) {
        BloodRequisition bloodRequisition = bloodRequisitionMapper.toEntity(bloodRequisitionRequest);
        bloodRequisition = bloodRequisitionService.createBloodRequisition(bloodRequisition);
        BloodRequisitionResponse donationResponse = bloodRequisitionMapper.toResponse(bloodRequisition);
        return ResponseEntity.ok(donationResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodRequisitionResponse> updateBloodRequisitionById(@RequestBody BloodRequisitionRequest bloodRequisitionRequest, @PathVariable Long id){
        BloodRequisition bloodRequisition = bloodRequisitionMapper.toEntity(bloodRequisitionRequest);
        bloodRequisition = bloodRequisitionService.updateBloodRequisitionById(bloodRequisition, id);
        BloodRequisitionResponse donationResponse = bloodRequisitionMapper.toResponse(bloodRequisition);
        return ResponseEntity.ok(donationResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodRequisitionById(@PathVariable Long id){
        bloodRequisitionService.deleteBloodRequisitionById(id);
        return ResponseEntity.ok().build();
    }
}
