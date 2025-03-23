package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.District;
import com.ciphertext.opencarebackend.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sadman
 */
@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> getAllByDivision(Division division);
}
