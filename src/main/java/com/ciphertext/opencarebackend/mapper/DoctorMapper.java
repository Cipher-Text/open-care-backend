package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.DoctorRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorResponse;
import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DoctorMapper {

    @Mapping(target = "yearOfExperience", expression = "java(calculateYearsOfExperience(doctor.getStartDate()))")
    DoctorResponse toResponse(Doctor doctor);

    Doctor toEntity(DoctorRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(DoctorRequest request, @MappingTarget Doctor doctor);

    default Integer calculateYearsOfExperience(LocalDate startDate) {
        if (startDate == null) {
            return null;
        }
        return java.time.Period.between(startDate, java.time.LocalDate.now()).getYears();
    }
}