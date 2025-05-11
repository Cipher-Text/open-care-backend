package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.ProfileRequest;
import com.ciphertext.opencarebackend.dto.response.ProfileResponse;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.enums.BloodGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.UserType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ProfileMapper {
    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderEnumToString")
    @Mapping(source = "userType", target = "userType", qualifiedByName = "userTypeEnumToString")
    @Mapping(source = "bloodGroup", target = "bloodGroup", qualifiedByName = "bloodGroupEnumToString")
    ProfileResponse toResponse(Profile profile);

    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderStringToEnum")
    @Mapping(source = "userType", target = "userType", qualifiedByName = "userTypeStringToEnum")
    @Mapping(source = "bloodGroup", target = "bloodGroup", qualifiedByName = "bloodGroupStringToEnum")
    Profile toEntity(ProfileRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ProfileRequest request, @MappingTarget Profile profile);

    @Named("genderEnumToString")
    default String genderEnumToString(Gender gender) {
        return gender != null ? gender.name() : null;
    }

    @Named("genderStringToEnum")
    default Gender genderStringToEnum(String gender) {
        return gender != null ? Gender.valueOf(gender) : null;
    }

    @Named("userTypeEnumToString")
    default String userTypeEnumToString(UserType userType) {
        return userType != null ? userType.name() : null;
    }

    @Named("userTypeStringToEnum")
    default UserType userTypeStringToEnum(String userType) {
        return userType != null ? UserType.valueOf(userType) : null;
    }

    @Named("bloodGroupEnumToString")
    default String bloodGroupEnumToString(BloodGroup bloodGroup) {
        return bloodGroup != null ? bloodGroup.name() : null;
    }

    @Named("bloodGroupStringToEnum")
    default BloodGroup bloodGroupStringToEnum(String bloodGroup) {
        return bloodGroup != null ? BloodGroup.valueOf(bloodGroup) : null;
    }
}