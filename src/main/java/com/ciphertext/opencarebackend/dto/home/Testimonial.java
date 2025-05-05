package com.ciphertext.opencarebackend.dto.home;

public class Testimonial {
    private Long id;
    private String name;
    private String avatarUrl;
    private int rating;
    private String content;

    public Testimonial() {}

    public Testimonial(Long id, String name, String avatarUrl, int rating, String content) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.rating = rating;
        this.content = content;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}