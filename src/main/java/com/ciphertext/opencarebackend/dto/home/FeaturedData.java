package com.ciphertext.opencarebackend.dto.home;

import java.util.List;

public class FeaturedData {
    private List<Doctor> doctors;
    private List<Hospital> hospitals;
    private List<Institute> institutes;
    private Stats stats;

    public FeaturedData() {}

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<Institute> getInstitutes() {
        return institutes;
    }

    public void setInstitutes(List<Institute> institutes) {
        this.institutes = institutes;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
