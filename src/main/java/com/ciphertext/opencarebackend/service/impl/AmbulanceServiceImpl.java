package com.ciphertext.opencarebackend.service.impl;


import com.ciphertext.opencarebackend.entity.Ambulance;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.AmbulanceRepository;
import com.ciphertext.opencarebackend.service.AmbulanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmbulanceServiceImpl implements AmbulanceService {

    private final AmbulanceRepository ambulanceRepository;

    @Override
    public Page<Ambulance> getPaginatedDataWithFilters(Pageable pagingSort) {
        return ambulanceRepository.findAll(pagingSort);
    }

    @Override
    public List<Ambulance> getAllAmbulance() {
        return ambulanceRepository.findAll();
    }

    @Override
    public Ambulance getAmbulanceById(Long id) {
        return ambulanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambulance not found with id: " + id));
    }

    @Override
    public Ambulance createAmbulance(Ambulance ambulance) {
        return ambulanceRepository.save(ambulance);
    }

    @Override
    public Ambulance updateAmbulanceById(Ambulance ambulance, Long id) {
        Ambulance existingAmbulance = ambulanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambulance not found with id: " + id));
        ambulance.setId(id);
        ambulance.setCreatedBy(existingAmbulance.getCreatedBy());
        ambulance.setCreatedAt(existingAmbulance.getCreatedAt());
        return ambulanceRepository.save(ambulance);
    }

    @Override
    public void deleteAmbulanceById(Long id) {
        Ambulance existingAmbulance = ambulanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ambulance not found with id: " + id));
        ambulanceRepository.delete(existingAmbulance);
    }
}
