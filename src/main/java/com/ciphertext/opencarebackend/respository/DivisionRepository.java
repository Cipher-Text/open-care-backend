package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Sadman
 */
@Repository
public interface DivisionRepository extends JpaRepository<Division, Integer> {
}
