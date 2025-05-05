package com.ciphertext.opencarebackend.dto.home;

public class Stats {
    private int totalDoctors;
    private int totalHospitals;
    private int totalInstitutes;
    private int totalPatientsCared;

    public Stats() {}

    public Stats(int totalDoctors, int totalHospitals, int totalInstitutes, int totalPatientsCared) {
        this.totalDoctors = totalDoctors;
        this.totalHospitals = totalHospitals;
        this.totalInstitutes = totalInstitutes;
        this.totalPatientsCared = totalPatientsCared;
    }

    public int getTotalDoctors() {
        return totalDoctors;
    }

    public void setTotalDoctors(int totalDoctors) {
        this.totalDoctors = totalDoctors;
    }

    public int getTotalHospitals() {
        return totalHospitals;
    }

    public void setTotalHospitals(int totalHospitals) {
        this.totalHospitals = totalHospitals;
    }

    public int getTotalInstitutes() {
        return totalInstitutes;
    }

    public void setTotalInstitutes(int totalInstitutes) {
        this.totalInstitutes = totalInstitutes;
    }

    public int getTotalPatientsCared() {
        return totalPatientsCared;
    }

    public void setTotalPatientsCared(int totalPatientsCared) {
        this.totalPatientsCared = totalPatientsCared;
    }
}
