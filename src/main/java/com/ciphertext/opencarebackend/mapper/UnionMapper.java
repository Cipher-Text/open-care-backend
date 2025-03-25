package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.UnionResponse;
import com.ciphertext.opencarebackend.entity.Union;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface UnionMapper {
    @Mapping(source = "upazila", target = "upazila")
    UnionResponse toResponse(Union union);
}
