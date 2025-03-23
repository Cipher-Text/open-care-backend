package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.MedicalSpeciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sadman
 */
@Repository
public interface MedicalSpecialityRepository extends JpaRepository<MedicalSpeciality, Integer> {
}
