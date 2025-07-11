package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.AdvertisementRequest;
import com.ciphertext.opencarebackend.dto.response.AdvertisementResponse;
import com.ciphertext.opencarebackend.entity.Advertisement;
import com.ciphertext.opencarebackend.mapper.AdvertisementMapper;
import com.ciphertext.opencarebackend.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/advertisements")
@RequiredArgsConstructor
public class AdvertisementApiController {

    private final AdvertisementService advertisementService;
    private final AdvertisementMapper advertisementMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllAdvertisementsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<Advertisement> pageAds = advertisementService.getPaginatedDataWithFilters(pagingSort);
        Page<AdvertisementResponse> adResponses = pageAds.map(advertisementMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("advertisements", adResponses.getContent());
        response.put("currentPage", pageAds.getNumber());
        response.put("totalItems", pageAds.getTotalElements());
        response.put("totalPages", pageAds.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdvertisementResponse>> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.getAllAdvertisement();
        List<AdvertisementResponse> adResponses = advertisements.stream()
                .map(advertisementMapper::toResponse)
                .toList();
        return ResponseEntity.ok(adResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> getAdvertisementById(@PathVariable Long id) {
        Advertisement ad = advertisementService.getAdvertisementById(id);
        AdvertisementResponse adResponse = advertisementMapper.toResponse(ad);
        return ResponseEntity.ok(adResponse);
    }

    @PostMapping("")
    public ResponseEntity<AdvertisementResponse> createAdvertisement(@RequestBody AdvertisementRequest adRequest) {
        Advertisement ad = advertisementMapper.toEntity(adRequest);
        ad = advertisementService.createAdvertisement(ad);
        AdvertisementResponse adResponse = advertisementMapper.toResponse(ad);
        return ResponseEntity.ok(adResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementResponse> updateAdvertisementById(@RequestBody AdvertisementRequest adRequest, @PathVariable Long id) {
        Advertisement ad = advertisementMapper.toEntity(adRequest);
        ad = advertisementService.updateAdvertisementById(ad, id);
        AdvertisementResponse adResponse = advertisementMapper.toResponse(ad);
        return ResponseEntity.ok(adResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisementById(@PathVariable Long id) {
        advertisementService.deleteAdvertisementById(id);
        return ResponseEntity.ok().build();
    }
}
