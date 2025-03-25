package com.ciphertext.opencarebackend.controller;

import com.ciphertext.opencarebackend.enums.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EnumApiController {

    @GetMapping("/blood-groups")
    public List<BloodGroup> getAllBloodGroups() {
        return Arrays.asList(BloodGroup.values());
    }

    @GetMapping("countries")
    public List<Country> getAllCountries() {
        return Arrays.asList(Country.values());
    }

    @GetMapping("/days-of-week")
    public List<DaysOfWeek> getAllDaysOfWeek() {
        return Arrays.asList(DaysOfWeek.values());
    }

    @GetMapping("/degree-types")
    public List<DegreeType> getAllDegreeTypes() {
        return Arrays.asList(DegreeType.values());
    }

    @GetMapping("/gender")
    public List<Gender> getAllGenders() {
        return Arrays.asList(Gender.values());
    }

    @GetMapping("/hospital-types")
    public List<HospitalType> getAllHospitalTypes() {
        return Arrays.asList(HospitalType.values());
    }

    @GetMapping("/organization-types")
    public List<OrganizationType> getAllOrganizationTypes() {
        return Arrays.asList(OrganizationType.values());
    }

    @GetMapping("/social-organization-types")
    public List<SocialOrganizationType> getAllSocialOrganizationTypes() {
        return Arrays.asList(SocialOrganizationType.values());
    }

    @GetMapping("/teacher-positions")
    public List<TeacherPosition> getAllTeacherPositions() {
        return Arrays.asList(TeacherPosition.values());
    }

}
