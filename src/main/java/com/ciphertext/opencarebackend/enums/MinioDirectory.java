package com.ciphertext.opencarebackend.enums;

public enum MinioDirectory {
    PROFILE("profiles"),
    DOCTOR("doctors"),
    PATIENT("patients"),
    HOSPITAL("hospitals"),
    INSTITUTION("institutions");

    private final String directory;

    MinioDirectory(String directory) {
        this.directory = directory;
    }

    public String getDirectory() {
        return directory;
    }
}
