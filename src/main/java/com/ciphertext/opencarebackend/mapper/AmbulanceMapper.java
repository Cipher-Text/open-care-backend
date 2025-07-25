package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.AmbulanceRequest;
import com.ciphertext.opencarebackend.dto.response.AmbulanceResponse;
import com.ciphertext.opencarebackend.entity.Ambulance;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface AmbulanceMapper {

    AmbulanceResponse toResponse(Ambulance ambulance);
    
    Ambulance toEntity(AmbulanceRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(AmbulanceRequest request, @MappingTarget Ambulance ambulance);


}