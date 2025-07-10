package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.BloodDonation;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.BloodDonationRepository;
import com.ciphertext.opencarebackend.service.BloodDonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodDonationServiceImpl implements BloodDonationService {

    private final BloodDonationRepository bloodDonationRepository;

    @Override
    public Page<BloodDonation> getPaginatedDataWithFilters(Pageable pagingSort) {
        return bloodDonationRepository.findAll(pagingSort);
    }

    @Override
    public List<BloodDonation> getAllBloodDonor() {
        return bloodDonationRepository.findAll();
    }

    @Override
    public BloodDonation getBloodDonorById(Long id) {
        return bloodDonationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood donor not found with id: " + id));
    }

    @Override
    public BloodDonation createBloodDonation(BloodDonation bloodDonation) {
        return bloodDonationRepository.save(bloodDonation);
    }

    @Override
    public BloodDonation updateBloodDonationById(BloodDonation bloodDonation, Long id) {
        BloodDonation existingBloodDonation = bloodDonationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood donor not found with id: " + id));
        bloodDonation.setId(id);
        return bloodDonationRepository.save(bloodDonation);
    }

    @Override
    public void deleteBloodDonationById(Long id) {
        BloodDonation existingBloodDonation = bloodDonationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood donor not found with id: " + id));
        bloodDonationRepository.delete(existingBloodDonation);

    }
}
