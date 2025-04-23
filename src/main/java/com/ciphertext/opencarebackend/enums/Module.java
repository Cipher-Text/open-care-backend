package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Module {
    DOCTOR("Doctor", "ডাক্তার", true),
    HOSPITAL("Hospital", "হাসপাতাল", true),
    ORGANIZATION("Organization", "সংগঠন", false),
    INSTITUTION("Institution", "প্রতিষ্ঠান", true);

    private final String name;
    private final String banglaName;
    private final Boolean isAdvertisement;

    Module(String name, String banglaName, Boolean isAdvertisement) {
        this.name = name;
        this.banglaName = banglaName;
        this.isAdvertisement = isAdvertisement;
    }

    public String getName() {
        return name;
    }

    public String getBanglaName() {
        return banglaName;
    }

    public Boolean getIsAdvertisement() {
        return isAdvertisement;
    }

    public static List<String> getAllEnumNamesByIsAdvertisement(Boolean isAdvertisement) {
        return Arrays.stream(Module.values())
                .filter(module -> module.getIsAdvertisement().equals(isAdvertisement))
                .map(Module::getName)
                .collect(Collectors.toList());
    }
}
