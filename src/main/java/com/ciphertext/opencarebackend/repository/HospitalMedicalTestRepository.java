package com.ciphertext.opencarebackend.repository;

import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Sadman
 */
@Repository
public interface HospitalMedicalTestRepository extends JpaRepository<HospitalMedicalTest, Long>, JpaSpecificationExecutor<HospitalMedicalTest> {

    long countAllByHospital_Id(int hospitalId);
}
