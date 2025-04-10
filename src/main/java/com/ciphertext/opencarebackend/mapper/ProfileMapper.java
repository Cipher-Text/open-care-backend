package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.ProfileResponse;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.UserType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ProfileMapper {
    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderEnumToString")
    @Mapping(source = "userType", target = "userType", qualifiedByName = "userTypeEnumToString")
    ProfileResponse toResponse(Profile profile);

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
}