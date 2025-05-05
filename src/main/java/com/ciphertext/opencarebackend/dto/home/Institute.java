package com.ciphertext.opencarebackend.dto.home;

import java.util.List;

public class Institute {
    private Long id;
    private String name;
    private String location;
    private String image;
    private String address;
    private String contact;
    private List<String> courses;
    private int established;

    public Institute() {}

    public Institute(Long id, String name, String location, String image,
                     String address, String contact, List<String> courses, int established) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.image = image;
        this.address = address;
        this.contact = contact;
        this.courses = courses;
        this.established = established;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public int getEstablished() {
        return established;
    }

    public void setEstablished(int established) {
        this.established = established;
    }
}