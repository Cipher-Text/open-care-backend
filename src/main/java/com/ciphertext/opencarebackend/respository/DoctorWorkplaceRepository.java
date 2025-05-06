package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sadman
 */
@Repository
public interface DoctorWorkplaceRepository extends JpaRepository<DoctorWorkplace, Long> {
    List<DoctorWorkplace> findByDoctorId(Long doctorId);
}
