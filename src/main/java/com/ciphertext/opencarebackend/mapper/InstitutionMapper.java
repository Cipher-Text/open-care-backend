package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.InstitutionResponse;
import com.ciphertext.opencarebackend.entity.Institution;
import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface InstitutionMapper {
    @Mapping(source = "hospitalType", target = "hospitalType", qualifiedByName = "hospitalEnumToString")
    @Mapping(source = "organizationType", target = "organizationType", qualifiedByName = "organizationEnumToString")
    InstitutionResponse toResponse(Institution institution);

    @Named("hospitalEnumToString")
    default String hospitalEnumToString(HospitalType hospitalType) {
        return hospitalType != null ? hospitalType.name() : null;
    }

    @Named("hospitalStringToEnum")
    default HospitalType hospitalStringToEnum(String hospitalType) {
        return hospitalType != null ? HospitalType.valueOf(hospitalType) : null;
    }

    @Named("organizationEnumToString")
    default String organizationEnumToString(OrganizationType organizationType) {
        return organizationType != null ? organizationType.name() : null;
    }

    @Named("organizationStringToEnum")
    default OrganizationType organizationStringToEnum(String organizationType) {
        return organizationType != null ? OrganizationType.valueOf(organizationType) : null;
    }
}