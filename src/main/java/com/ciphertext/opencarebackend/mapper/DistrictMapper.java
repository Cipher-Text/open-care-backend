package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.DistrictResponse;
import com.ciphertext.opencarebackend.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface DistrictMapper {
    @Mapping(source = "division", target = "division")
    DistrictResponse toResponse(District district);
}
