package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Ambulance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AmbulanceService {
    Page<Ambulance> getPaginatedDataWithFilters(Pageable pagingSort);

    List<Ambulance> getAllAmbulance();

    Ambulance getAmbulanceById(Long id);

    Ambulance createAmbulance(Ambulance ambulance);

    Ambulance updateAmbulanceById(Ambulance ambulance, Long id);

    void deleteAmbulanceById(Long id);
}
