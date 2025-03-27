package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.ProfileResponse;
import com.ciphertext.opencarebackend.entity.Profile;
import com.ciphertext.opencarebackend.enums.Gender;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ProfileMapper {
    @Mapping(source = "gender", target = "gender", qualifiedByName = "genderEnumToString")
    ProfileResponse toResponse(Profile profile);

    @Named("genderEnumToString")
    default String genderEnumToString(Gender gender) {
        return gender != null ? gender.name() : null;
    }

    @Named("genderStringToEnum")
    default Gender genderStringToEnum(String gender) {
        return gender != null ? Gender.valueOf(gender) : null;
    }
}