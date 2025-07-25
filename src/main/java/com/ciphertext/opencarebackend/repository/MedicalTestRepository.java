package com.ciphertext.opencarebackend.repository;

import com.ciphertext.opencarebackend.entity.MedicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalTestRepository extends JpaRepository<MedicalTest, Integer>, JpaSpecificationExecutor<MedicalTest> {
}