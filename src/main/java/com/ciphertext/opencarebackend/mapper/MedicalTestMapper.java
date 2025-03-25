package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.MedicalTestResponse;
import com.ciphertext.opencarebackend.entity.MedicalTest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface MedicalTestMapper {
    MedicalTestResponse toResponse(MedicalTest medicalTest);
}
