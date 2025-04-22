package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.MedicalTestRequest;
import com.ciphertext.opencarebackend.dto.response.MedicalTestResponse;
import com.ciphertext.opencarebackend.entity.MedicalTest;
import com.ciphertext.opencarebackend.entity.MedicalTest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface MedicalTestMapper {
    MedicalTestResponse toResponse(MedicalTest medicalTest);

    MedicalTest toEntity(MedicalTestRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(MedicalTestRequest request, @MappingTarget MedicalTest medicalTest);
}
