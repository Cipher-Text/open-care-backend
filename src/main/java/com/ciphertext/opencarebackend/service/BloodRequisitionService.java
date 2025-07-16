package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.BloodRequisition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BloodRequisitionService {
    Page<BloodRequisition> getPaginatedDataWithFilters(Pageable pagingSort);

    List<BloodRequisition> getAllBloodRequisition();

    BloodRequisition getBloodRequisitionById(Long id);

    BloodRequisition createBloodRequisition(BloodRequisition bloodRequisition);

    BloodRequisition updateBloodRequisitionById(BloodRequisition bloodRequisition, Long id);

    void deleteBloodRequisitionById(Long id);
}
