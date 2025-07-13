package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.AdvertisementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertisementTypeService {
    Page<AdvertisementType> getPaginatedDataWithFilters(Pageable pagingSort);

    List<AdvertisementType> getAllAdvertisementTypes();

    AdvertisementType getAdvertisementTypeById(Integer id);

    AdvertisementType createAdvertisementType(AdvertisementType advertisementType);

    AdvertisementType updateAdvertisementTypeById(AdvertisementType advertisementType, Integer id);

    void deleteAdvertisementTypeById(Integer id);
}
