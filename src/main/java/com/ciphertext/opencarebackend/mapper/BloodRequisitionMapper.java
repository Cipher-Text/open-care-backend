package com.ciphertext.opencarebackend.mapper;

import com.ciphertext.opencarebackend.dto.request.BloodRequisitionRequest;
import com.ciphertext.opencarebackend.dto.response.BloodRequisitionResponse;
import com.ciphertext.opencarebackend.entity.*;
import com.ciphertext.opencarebackend.enums.BloodComponent;
import com.ciphertext.opencarebackend.enums.BloodGroup;
import com.ciphertext.opencarebackend.enums.Gender;
import com.ciphertext.opencarebackend.enums.RequisitionStatus;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface BloodRequisitionMapper {

    BloodRequisitionResponse toResponse(BloodRequisition bloodRequisition);

    @Mapping(source = "status", target = "requisitionStatus", qualifiedByName = "statusToRequisitionStatus")
    @Mapping(source = "requesterId", target = "requester.id")
    @Mapping(source = "bloodGroup", target = "bloodGroup", qualifiedByName = "mapBloodGroup")
    @Mapping(source = "bloodComponent", target = "bloodComponent", qualifiedByName = "mapBloodComponent")
    @Mapping(source = "hospitalId", target = "hospital.id")
    @Mapping(source = "districtId", target = "district.id")
    @Mapping(source = "upazilaId", target = "upazila.id")
    @Mapping(source = "patientGender", target = "patientGender", qualifiedByName = "mapGender")
    BloodRequisition toEntity(BloodRequisitionRequest request);

    @Named("statusToRequisitionStatus")
    default RequisitionStatus statusToRequisitionStatus(String status) {
        return status != null ? RequisitionStatus.valueOf(status) : null;
    }

    @Named("mapBloodGroup")
    default BloodGroup mapBloodGroup(String bloodGroup) {
        return bloodGroup != null ? BloodGroup.valueOf(bloodGroup) : null;
    }

    @Named("mapBloodComponent")
    default BloodComponent mapBloodComponent(String component) {
        return component != null ? BloodComponent.valueOf(component) : null;
    }

    @Named("mapGender")
    default Gender mapGender(String gender) {
        return gender != null ? Gender.valueOf(gender) : null;
    }

}
