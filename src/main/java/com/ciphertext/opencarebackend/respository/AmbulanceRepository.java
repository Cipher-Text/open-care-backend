package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.Ambulance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbulanceRepository extends JpaRepository<Ambulance, Long> {

}
