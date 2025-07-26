package com.ciphertext.opencarebackend.repository;

import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Sadman
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>, JpaSpecificationExecutor<Profile> {
    Optional<Profile> findByKeycloakUserId(String keycloakUserId);

    Long countAllByUserType(UserType userType);
}
