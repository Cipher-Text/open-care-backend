package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.AdvertisementRequest;
import com.ciphertext.opencarebackend.dto.request.AdvertisementTypeRequest;
import com.ciphertext.opencarebackend.dto.response.AdvertisementTypeResponse;
import com.ciphertext.opencarebackend.entity.Advertisement;
import com.ciphertext.opencarebackend.entity.AdvertisementType;
import com.ciphertext.opencarebackend.enums.AdvertisementPosition;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface AdvertisementTypeMapper {

    @Mapping(source = "position", target = "position", qualifiedByName = "positionEnumToString")
    AdvertisementTypeResponse toResponse(AdvertisementType advertisementType);

    @Mapping(source = "position", target = "position", qualifiedByName = "positionStringToEnum")
    AdvertisementType toEntity(AdvertisementTypeRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(AdvertisementRequest request, @MappingTarget Advertisement advertisement);

    @Named("positionEnumToString")
    default String positionEnumToString(AdvertisementPosition advertisementPosition) {
        return advertisementPosition != null ? advertisementPosition.name() : null;
    }

    @Named("positionStringToEnum")
    default AdvertisementPosition positionStringToEnum(String advertisementPosition) {
        return advertisementPosition != null ? AdvertisementPosition.valueOf(advertisementPosition) : null;
    }
}