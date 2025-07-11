package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.dto.request.AdvertisementTypeRequest;
import com.ciphertext.opencarebackend.dto.response.AdvertisementTypeResponse;
import com.ciphertext.opencarebackend.entity.AdvertisementType;
import com.ciphertext.opencarebackend.mapper.AdvertisementTypeMapper;
import com.ciphertext.opencarebackend.service.AdvertisementTypeService;
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
@RequestMapping("/api/advertisement-types")
@RequiredArgsConstructor
public class AdvertisementTypeApiController {

    private final AdvertisementTypeService advertisementTypeService;
    private final AdvertisementTypeMapper advertisementTypeMapper;

    @GetMapping("")
    public ResponseEntity<Map<String, Object>> getAllAdvertisementTypesPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pagingSort = PageRequest.of(page, size, Sort.Direction.DESC, "id");
        Page<AdvertisementType> pageTypes = advertisementTypeService.getPaginatedDataWithFilters(pagingSort);
        Page<AdvertisementTypeResponse> typeResponses = pageTypes.map(advertisementTypeMapper::toResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("types", typeResponses.getContent());
        response.put("currentPage", pageTypes.getNumber());
        response.put("totalItems", pageTypes.getTotalElements());
        response.put("totalPages", pageTypes.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AdvertisementTypeResponse>> getAllAdvertisementTypes() {
        List<AdvertisementType> types = advertisementTypeService.getAllAdvertisementTypes();
        List<AdvertisementTypeResponse> typeResponses = types.stream()
                .map(advertisementTypeMapper::toResponse)
                .toList();
        return ResponseEntity.ok(typeResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementTypeResponse> getAdvertisementTypeById(@PathVariable Integer id) {
        AdvertisementType type = advertisementTypeService.getAdvertisementTypeById(id);
        AdvertisementTypeResponse typeResponse = advertisementTypeMapper.toResponse(type);
        return ResponseEntity.ok(typeResponse);
    }

    @PostMapping("")
    public ResponseEntity<AdvertisementTypeResponse> createAdvertisementType(@RequestBody AdvertisementTypeRequest typeRequest) {
        AdvertisementType type = advertisementTypeMapper.toEntity(typeRequest);
        type = advertisementTypeService.createAdvertisementType(type);
        AdvertisementTypeResponse typeResponse = advertisementTypeMapper.toResponse(type);
        return ResponseEntity.ok(typeResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementTypeResponse> updateAdvertisementTypeById(@RequestBody AdvertisementTypeRequest typeRequest, @PathVariable Integer id) {
        AdvertisementType type = advertisementTypeMapper.toEntity(typeRequest);
        type = advertisementTypeService.updateAdvertisementTypeById(type, id);
        AdvertisementTypeResponse typeResponse = advertisementTypeMapper.toResponse(type);
        return ResponseEntity.ok(typeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisementTypeById(@PathVariable Integer id) {
        advertisementTypeService.deleteAdvertisementTypeById(id);
        return ResponseEntity.ok().build();
    }
}
