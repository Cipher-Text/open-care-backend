package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.UpazilaResponse;
import com.ciphertext.opencarebackend.entity.Upazila;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface UpazilaMapper {
    @Mapping(source = "district", target = "district")
    UpazilaResponse toResponse(Upazila upazila);
}
