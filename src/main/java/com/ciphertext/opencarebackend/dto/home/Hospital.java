package com.ciphertext.opencarebackend.dto.home;

import java.util.List;

public class Hospital {
    private Long id;
    private String name;
    private String address;
    private String image;
    private String type;
    private int beds;
    private String contact;
    private List<String> specialties;
    private double rating;
    private List<Doctor> doctors;

    public Hospital() {}

    public Hospital(Long id, String name, String address, String image, String type,
                    int beds, String contact, List<String> specialties, double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.image = image;
        this.type = type;
        this.beds = beds;
        this.contact = contact;
        this.specialties = specialties;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBeds() {
        return beds;
    }

    public void setBeds(int beds) {
        this.beds = beds;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<String> specialties) {
        this.specialties = specialties;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}