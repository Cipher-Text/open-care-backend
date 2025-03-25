package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.MedicalSpecialityResponse;
import com.ciphertext.opencarebackend.entity.MedicalSpeciality;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface MedicalSpecialityMapper {
    MedicalSpecialityResponse toResponse(MedicalSpeciality medicalSpeciality);
}
