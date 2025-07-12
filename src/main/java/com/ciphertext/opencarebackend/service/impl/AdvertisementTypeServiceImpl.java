package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.AdvertisementType;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.AdvertisementTypeRepository;
import com.ciphertext.opencarebackend.service.AdvertisementTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementTypeServiceImpl implements AdvertisementTypeService {

    private final AdvertisementTypeRepository advertisementTypeRepository;

    @Override
    public Page<AdvertisementType> getPaginatedDataWithFilters(Pageable pageable) {
        return advertisementTypeRepository.findAll(pageable);
    }

    @Override
    public List<AdvertisementType> getAllAdvertisementTypes() {
        return advertisementTypeRepository.findAll();
    }

    @Override
    public AdvertisementType getAdvertisementTypeById(Integer id) {
        return advertisementTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AdvertisementType not found with id: " + id));
    }

    @Override
    public AdvertisementType createAdvertisementType(AdvertisementType advertisementType) {
        return advertisementTypeRepository.save(advertisementType);
    }

    @Override
    public AdvertisementType updateAdvertisementTypeById(AdvertisementType advertisementType, Integer id) {
        AdvertisementType existing = advertisementTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AdvertisementType not found with id: " + id));
        advertisementType.setId(id);
        return advertisementTypeRepository.save(advertisementType);
    }

    @Override
    public void deleteAdvertisementTypeById(Integer id) {
        AdvertisementType existing = advertisementTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AdvertisementType not found with id: " + id));
        advertisementTypeRepository.delete(existing);
    }
}
