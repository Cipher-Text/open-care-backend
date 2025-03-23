package com.ciphertext.opencarebackend.dto.response;

import com.ciphertext.opencarebackend.entity.DoctorWorkplace;
import com.ciphertext.opencarebackend.enums.DaysOfWeek;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class DoctorScheduleResponse {
    private Long id;
    private DoctorWorkplaceResponse doctorWorkplace;
    private DaysOfWeek daysOfWeek;
    private Time startTime;
    private Time endTime;
}
