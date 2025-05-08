package com.ciphertext.opencarebackend.dto.home;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Stats {
    private Long totalDoctors;
    private Long totalHospitals;
    private Long totalInstitutes;
    private Long totalUsers;
}
