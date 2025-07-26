package com.ciphertext.opencarebackend.repository;

import com.ciphertext.opencarebackend.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Sadman
 */
@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer>, JpaSpecificationExecutor<Institution> {
}