package com.ciphertext.opencarebackend.dto.home;

public class Doctor {
    private Long id;
    private String name;
    private String specialization;
    private int experience;
    private String image;
    private double rating;
    private int reviewCount;
    private String education;
    private String contact;

    public Doctor() {}

    public Doctor(Long id, String name, String specialization, int experience, String image,
                  double rating, int reviewCount, String education, String contact) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.experience = experience;
        this.image = image;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.education = education;
        this.contact = contact;
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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}