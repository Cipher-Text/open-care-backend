package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ContributionBadge {
    FIRST_STEP(1, 100, "👣", "First Step", "You've started your journey to make healthcare smarter."),
    ACTIVE_SCOUT(101, 250, "🕵️", "Active Scout", "You're actively scouting the medical world for improvements."),
    FACT_CHECKER(251, 500, "📘", "Fact Checker", "Your efforts ensure our data stays accurate and reliable."),
    HEALTH_EXPLORER(501, 1000, "🗺️", "Health Explorer", "You're helping others explore and access better healthcare options."),
    COMMUNITY_PILLAR(1001, 2000, "🏛️", "Community Pillar", "Your support is strengthening the healthcare network."),
    TRUSTED_CONTRIBUTOR(2001, 4000, "🤝", "Trusted Contributor", "Your consistency makes you a dependable contributor."),
    HEALTH_INFLUENCER(4001, 6000, "📣", "Health Influencer", "Your contributions influence healthcare choices."),
    KNOWLEDGE_HERO(6001, 8000, "🧠", "Knowledge Hero", "You're building a reliable source of healthcare truth."),
    OPEN_CARE_CHAMPION(8001, 10000, "🏆", "Open Care Champion", "Your impact in Open Care is making lives easier."),
    LEGENDARY_CONTRIBUTOR(10001, Integer.MAX_VALUE, "🌟", "Legendary Contributor", "You're a true visionary of open healthcare.");

    private final int minPoints;
    private final int maxPoints;
    private final String icon;
    private final String title;
    private final String description;

    ContributionBadge(int minPoints, int maxPoints, String icon, String title, String description) {
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

    public static ContributionBadge fromPoints(int points) {
        for (ContributionBadge badge : ContributionBadge.values()) {
            if (points >= badge.getMinPoints() && points <= badge.getMaxPoints()) {
                return badge;
            }
        }
        return null;
    }
}

