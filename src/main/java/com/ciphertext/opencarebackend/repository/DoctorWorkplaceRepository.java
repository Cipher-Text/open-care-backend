package com.ciphertext.opencarebackend.repository;

import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sadman
 */
@Repository
public interface DoctorWorkplaceRepository extends JpaRepository<DoctorWorkplace, Long> {
    List<DoctorWorkplace> findByDoctorId(Long doctorId);

    @Query(value = """
              SELECT ms.id, ms.name, ms.bn_name, ms.icon, COUNT(dw.id) AS doctor_count
              FROM medical_speciality ms
              LEFT JOIN doctor_workplace dw ON ms.id = dw.medical_speciality_id AND dw.end_date IS NULL
              GROUP BY ms.id, ms.name, ms.bn_name, ms.icon
              ORDER BY doctor_count desc
              LIMIT ?1
                        """, nativeQuery = true)
    List<Object[]> findMedicalSpecialitiesWithDoctorCount(Integer limit);

    long countAllByHospital_Id(int hospitalId);
}
