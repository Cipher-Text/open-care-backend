package com.ciphertext.opencarebackend.service.impl;


import com.ciphertext.opencarebackend.entity.Advertisement;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;

import com.ciphertext.opencarebackend.respository.AdvertisementRepository;
import com.ciphertext.opencarebackend.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Override
    public Page<Advertisement> getPaginatedDataWithFilters(Pageable pagingSort) {
        return advertisementRepository.findAll(pagingSort);
    }

    @Override
    public List<Advertisement> getAllAdvertisement() {
        return advertisementRepository.findAll();
    }

    @Override
    public Advertisement getAdvertisementById(Long id) {
        return advertisementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found with id: " + id));
    }

    @Override
    public Advertisement createAdvertisement(Advertisement advertisement) {
        return advertisementRepository.save(advertisement);
    }

    @Override
    public Advertisement updateAdvertisementById(Advertisement advertisement, Long id) {
        Advertisement existingAdvertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found with id: " + id));
        advertisement.setId(id);
        return advertisementRepository.save(advertisement);
    }

    @Override
    public void deleteAdvertisementById(Long id) {
        Advertisement existingAdvertisement = advertisementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found with id: " + id));
        advertisementRepository.delete(existingAdvertisement);
    }
}
