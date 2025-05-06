package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.DoctorDegree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sadman
 */
@Repository
public interface DoctorDegreeRepository extends JpaRepository<DoctorDegree, Long> {
    List<DoctorDegree> findByDoctorId(Long doctorId);
}
