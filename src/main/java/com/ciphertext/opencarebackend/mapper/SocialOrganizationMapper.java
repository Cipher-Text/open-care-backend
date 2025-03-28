package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.response.SocialOrganizationResponse;
import com.ciphertext.opencarebackend.entity.SocialOrganization;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface SocialOrganizationMapper {
    SocialOrganizationResponse toResponse(SocialOrganization socialOrganization);
}
