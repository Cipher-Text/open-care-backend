package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.MedicineResponse;
import com.ciphertext.opencarebackend.entity.Medicine;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface MedicineMapper {
    MedicineResponse toResponse(Medicine medicine);
}