package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Sadman
 */
@Repository
public interface DoctorRepository  extends JpaRepository<Doctor, Long>, JpaSpecificationExecutor<Doctor> {
}
