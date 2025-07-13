package com.ciphertext.opencarebackend.respository;

import com.ciphertext.opencarebackend.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

}
