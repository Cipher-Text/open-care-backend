package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.AdvertisementRequest;
import com.ciphertext.opencarebackend.dto.response.AdvertisementResponse;
import com.ciphertext.opencarebackend.entity.Advertisement;
import com.ciphertext.opencarebackend.enums.AgeGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface AdvertisementMapper {

    @Mapping(source = "ageGroup", target = "ageGroup", qualifiedByName = "ageGroupEnumToString")
    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderEnumToString")
    AdvertisementResponse toResponse(Advertisement advertisement);

    @Mapping(source = "ageGroup", target = "ageGroup", qualifiedByName = "ageGroupStringToEnum")
    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderStringToEnum")
    @Mapping(source = "advTypeId", target = "advTypeId")
//    @Mapping(source = "createdBy", target = "createdBy")
    Advertisement toEntity(AdvertisementRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(AdvertisementRequest request, @MappingTarget Advertisement advertisement);

    @Named("ageGroupEnumToString")
    default String ageGroupEnumToString(AgeGroup ageGroup) {
        return ageGroup != null ? ageGroup.name() : null;
    }

    @Named("ageGroupStringToEnum")
    default AgeGroup ageGroupStringToEnum(String ageGroup) {
        return ageGroup != null ? AgeGroup.valueOf(ageGroup) : null;
    }

    @Named("genderEnumToString")
    default String genderEnumToString(Gender gender) {
        return gender != null ? gender.name() : null;
    }

    @Named("genderStringToEnum")
    default Gender genderStringToEnum(String gender) {
        return gender != null ? Gender.valueOf(gender) : null;
    }


}