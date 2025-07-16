package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.BloodRequisition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodRequisitionRepository extends JpaRepository<BloodRequisition, Long> {
}
