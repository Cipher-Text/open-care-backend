package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.AmbulanceRequest;
import com.ciphertext.opencarebackend.dto.response.AmbulanceResponse;
import com.ciphertext.opencarebackend.entity.Ambulance;
import com.ciphertext.opencarebackend.enums.AmbulanceType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface AmbulanceMapper {

    @Mapping(source = "hospital", target = "hospital")
    @Mapping(source = "district", target = "district")
    @Mapping(source = "upazila", target = "upazila")
    @Mapping(source = "type", target = "type", qualifiedByName = "ambulanceTypeEnumToString")
    AmbulanceResponse toResponse(Ambulance ambulance);

    @Mapping(source = "hospitalId", target = "hospital.id")
    @Mapping(source = "districtId", target = "district.id")
    @Mapping(source = "upazilaId", target = "upazila.id")
    @Mapping(source = "type", target = "type", qualifiedByName = "ambulanceTypeStringToEnum")
    Ambulance toEntity(AmbulanceRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(AmbulanceRequest request, @MappingTarget Ambulance ambulance);

    @Named("ambulanceTypeEnumToString")
    default String ambulanceTypeEnumToString(AmbulanceType ambulanceType) {
        return ambulanceType != null ? ambulanceType.name() : null;
    }

    @Named("ambulanceTypeStringToEnum")
    default AmbulanceType ambulanceTypeStringToEnum(String ambulanceType) {
        return ambulanceType != null ? AmbulanceType.valueOf(ambulanceType) : null;
    }

}