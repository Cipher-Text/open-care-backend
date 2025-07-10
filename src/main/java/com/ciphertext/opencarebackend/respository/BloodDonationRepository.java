package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.BloodDonation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodDonationRepository extends JpaRepository<BloodDonation, Long> {
}
