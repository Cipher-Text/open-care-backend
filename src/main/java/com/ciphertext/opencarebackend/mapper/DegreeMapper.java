package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.DegreeResponse;
import com.ciphertext.opencarebackend.entity.Degree;
import com.ciphertext.opencarebackend.enums.DegreeType;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DegreeMapper {
    @Mapping(source = "degreeType", target = "degreeType", qualifiedByName = "enumToString")
    DegreeResponse toResponse(Degree degree);

//    @Mapping(source = "degreeType", target = "degreeType", qualifiedByName = "stringToEnum")
//    Degree toEntity(DegreeRequest request);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    void partialUpdate(DegreeRequest request, @MappingTarget Degree degree);

    @Named("enumToString")
    default String mapEnumToString(DegreeType degreeType) {
        return degreeType != null ? degreeType.name() : null;
    }

    @Named("stringToEnum")
    default DegreeType mapStringToEnum(String degreeType) {
        return degreeType != null ? DegreeType.valueOf(degreeType) : null;
    }
}