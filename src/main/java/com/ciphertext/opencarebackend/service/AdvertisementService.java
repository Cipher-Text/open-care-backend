package com.ciphertext.opencarebackend.service;

import com.ciphertext.opencarebackend.entity.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdvertisementService {
    Page<Advertisement> getPaginatedDataWithFilters(Pageable pagingSort);

    List<Advertisement> getAllAdvertisement();

    Advertisement getAdvertisementById(Long id);

    Advertisement createAdvertisement(Advertisement advertisement);

    Advertisement updateAdvertisementById(Advertisement advertisement, Long id);

    void deleteAdvertisementById(Long id);
}
