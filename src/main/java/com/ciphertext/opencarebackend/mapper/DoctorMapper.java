package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.DoctorRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorResponse;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DoctorMapper {
    DoctorResponse toResponse(Doctor doctor);

    Doctor toEntity(DoctorRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(DoctorRequest request, @MappingTarget Doctor doctor);

}