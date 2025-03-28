package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.Degree;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.entity.SocialOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Sadman
 */
@Repository
public interface SocialOrganizationRepository extends JpaRepository<SocialOrganization, Integer>, JpaSpecificationExecutor<SocialOrganization> {
}
