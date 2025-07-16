package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DoctorBadge {
    HEALING_BEGINNER(1, 100, "🩺", "Healing Beginner", "Taking your first steps in helping the community"),
    CARE_PROVIDER(101, 300, "🤲", "Care Provider", "Regularly offering your medical skills to those in need"),
    COMMUNITY_DOCTOR(301, 600, "🏠", "Community Doctor", "Your presence makes the neighborhood healthier"),
    WELLNESS_ADVOCATE(601, 1000, "🌱", "Wellness Advocate", "Championing good health for everyone, regardless of means"),
    TRUSTED_HEALER(1001, 2000, "💖", "Trusted Healer", "People count on your consistent and compassionate care"),
    HEALTH_GUARDIAN(2001, 3000, "🛡️", "Health Guardian", "Protecting the wellbeing of the underserved"),
    LIFE_SAVER(3001, 4000, "💉", "Life Saver", "Your interventions have made critical differences in lives"),
    HEALING_HERO(4001, 5000, "🦸", "Healing Hero", "Your extraordinary efforts have helped countless patients"),
    MEDICINE_MASTER(5001, 7000, "⚕️", "Medicine Master", "Your expertise shines through your generous service"),
    HEALTH_CHAMPION(7001, 9000, "🏆", "Health Champion", "A true champion for healthcare accessibility"),
    COMPASSION_STAR(9001, 12000, "⭐", "Compassion Star", "Your kindness lights the way for others to follow"),
    DOCTOR_LEGEND(12001, Integer.MAX_VALUE, "🌟", "Doctor Legend", "Your legendary commitment to service inspires everyone around you");

    private final int minPoints;
    private final int maxPoints;
    private final String icon;
    private final String title;
    private final String description;

    DoctorBadge(int minPoints, int maxPoints, String icon, String title, String description) {
        this.minPoints = minPoints;
        this.maxPoints = maxPoints;
        this.icon = icon;
        this.title = title;
        this.description = description;
    }

    public int getMinPoints() {
        return minPoints;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public String getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static DoctorBadge fromPoints(int points) {
        for (DoctorBadge badge : DoctorBadge.values()) {
            if (points >= badge.getMinPoints() && points <= badge.getMaxPoints()) {
                return badge;
            }
        }
        return null;
    }
    }