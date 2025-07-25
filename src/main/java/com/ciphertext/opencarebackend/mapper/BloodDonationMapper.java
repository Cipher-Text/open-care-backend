package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.BloodDonationRequest;
import com.ciphertext.opencarebackend.dto.response.BloodDonationResponse;
import com.ciphertext.opencarebackend.entity.BloodDonation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface BloodDonationMapper {

    BloodDonationResponse toResponse(BloodDonation bloodDonation);

    BloodDonation toEntity(BloodDonationRequest request);
}
