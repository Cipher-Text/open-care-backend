package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.HospitalMedicalTestRequest;
import com.ciphertext.opencarebackend.dto.response.HospitalMedicalTestResponse;
import com.ciphertext.opencarebackend.entity.HospitalMedicalTest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface HospitalMedicalTestMapper {
    HospitalMedicalTestResponse toResponse(HospitalMedicalTest hospitalMedicalTest);

    HospitalMedicalTest toEntity(HospitalMedicalTestRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(HospitalMedicalTestRequest request, @MappingTarget HospitalMedicalTest hospitalMedicalTestDegree);

}