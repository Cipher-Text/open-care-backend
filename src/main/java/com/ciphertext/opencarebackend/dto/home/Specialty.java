package com.ciphertext.opencarebackend.dto.home;

public class Specialty {
    private String name;
    private String icon;

    public Specialty() {}

    public Specialty(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}