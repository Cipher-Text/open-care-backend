package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.DoctorDegreeRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorDegreeResponse;
import com.ciphertext.opencarebackend.entity.DoctorDegree;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DoctorDegreeMapper {
    DoctorDegreeResponse toResponse(DoctorDegree doctorDegree);

    DoctorDegree toEntity(DoctorDegreeRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(DoctorDegreeRequest request, @MappingTarget DoctorDegree doctorDegreeDegree);

}