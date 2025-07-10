package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.BloodDonation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BloodDonationService {
    Page<BloodDonation> getPaginatedDataWithFilters(Pageable pagingSort);

    List<BloodDonation> getAllBloodDonor();

    BloodDonation getBloodDonorById(Long id);

    BloodDonation createBloodDonation(BloodDonation bloodDonation);

    BloodDonation updateBloodDonationById(BloodDonation bloodDonation, Long id);

    void deleteBloodDonationById(Long id);
}
