package com.ciphertext.opencarebackend.dto.home;

import com.ciphertext.opencarebackend.entity.Doctor;
import com.ciphertext.opencarebackend.entity.Hospital;
import com.ciphertext.opencarebackend.entity.Institution;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeaturedData {
    private List<Doctor> doctors;
    private List<Hospital> hospitals;
    private List<Institution> institutes;
    private Stats stats;
}
