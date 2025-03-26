package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.HospitalRequest;
import com.ciphertext.opencarebackend.dto.response.HospitalResponse;
import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.enums.HospitalType;
import com.ciphertext.opencarebackend.enums.OrganizationType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface HospitalMapper {
    @Mapping(source = "hospitalType", target = "hospitalType", qualifiedByName = "hospitalEnumToString")
    @Mapping(source = "organizationType", target = "organizationType", qualifiedByName = "organizationEnumToString")
    HospitalResponse toResponse(Hospital hospital);

    @Mapping(source = "hospitalType", target = "hospitalType", qualifiedByName = "hospitalStringToEnum")
    @Mapping(source = "organizationType", target = "organizationType", qualifiedByName = "organizationStringToEnum")
    @Mapping(source = "districtId", target = "district.id")
    @Mapping(source = "upazilaId", target = "upazila.id")
    @Mapping(source = "unionId", target = "union.id")
    Hospital toEntity(HospitalRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(HospitalRequest request, @MappingTarget Hospital hospital);

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