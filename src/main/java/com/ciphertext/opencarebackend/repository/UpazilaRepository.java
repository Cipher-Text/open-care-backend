package com.ciphertext.opencarebackend.repository;

import com.ciphertext.opencarebackend.entity.District;
import com.ciphertext.opencarebackend.entity.Upazila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sadman
 */
@Repository
public interface UpazilaRepository  extends JpaRepository<Upazila, Integer> {
    List<Upazila> getAllByDistrict(District district);
}
