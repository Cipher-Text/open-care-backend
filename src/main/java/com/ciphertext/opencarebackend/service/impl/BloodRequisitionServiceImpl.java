package com.ciphertext.opencarebackend.service.impl;

import com.ciphertext.opencarebackend.entity.BloodDonation;
import com.ciphertext.opencarebackend.entity.BloodRequisition;
import com.ciphertext.opencarebackend.exception.ResourceNotFoundException;
import com.ciphertext.opencarebackend.respository.BloodRequisitionRepository;
import com.ciphertext.opencarebackend.service.BloodRequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BloodRequisitionServiceImpl implements BloodRequisitionService {
    private final BloodRequisitionRepository bloodRequisitionRepository;

    @Override
    public Page<BloodRequisition> getPaginatedDataWithFilters(Pageable pagingSort) {
        return bloodRequisitionRepository.findAll(pagingSort);
    }

    @Override
    public List<BloodRequisition> getAllBloodRequisition() {
        return bloodRequisitionRepository.findAll();
    }

    @Override
    public BloodRequisition getBloodRequisitionById(Long id) {
        return bloodRequisitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood Requisition not found with id: " + id));
    }

    @Override
    public BloodRequisition createBloodRequisition(BloodRequisition bloodRequisition) {
        return bloodRequisitionRepository.save(bloodRequisition);
    }

    @Override
    public BloodRequisition updateBloodRequisitionById(BloodRequisition bloodRequisition, Long id) {
        BloodRequisition existingBloodRequisition = bloodRequisitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood Requisition not found with id: " + id));
        bloodRequisition.setId(id);
        return bloodRequisitionRepository.save(bloodRequisition);
    }

    @Override
    public void deleteBloodRequisitionById(Long id) {
        BloodRequisition existingBloodRequisition = bloodRequisitionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blood Requisition not found with id: " + id));
        bloodRequisitionRepository.delete(existingBloodRequisition);
    }
}
