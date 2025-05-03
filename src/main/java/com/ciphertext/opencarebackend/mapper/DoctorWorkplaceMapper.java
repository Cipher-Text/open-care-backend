package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.DoctorWorkplaceRequest;
import com.ciphertext.opencarebackend.dto.response.DoctorWorkplaceResponse;
import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import com.ciphertext.opencarebackend.enums.TeacherPosition;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DoctorWorkplaceMapper {
    @Mapping(target = "teacherPosition", source = "teacherPosition", qualifiedByName = "mapTeacherPosition")
    DoctorWorkplaceResponse toResponse(DoctorWorkplace doctorWorkplace);

    DoctorWorkplace toEntity(DoctorWorkplaceRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(DoctorWorkplaceRequest request, @MappingTarget DoctorWorkplace doctorWorkplaceDegree);

    @Named("mapTeacherPosition")
    default String mapTeacherPosition(TeacherPosition position) {
        return position != null ? position.name() : null;
    }
}