package com.ciphertext.opencarebackend.dto.home;

public class BlogPost {
    private Long id;
    private String title;
    private String image;
    private String category;
    private String description;

    public BlogPost() {}

    public BlogPost(Long id, String title, String image, String category, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.category = category;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}