package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sadman
 */
@Repository
public interface DegreeRepository extends JpaRepository<Degree, Integer> {
}
