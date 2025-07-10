package com.ciphertext.opencarebackend.enums;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum BloodDonorLevel {
    FIRST_FLOW(1, 5, "🩸", "First Flow", "You've begun your life-saving journey—thank you!"),
    HOPE_GIVER(6, 10, "🌱", "Hope Giver", "Each donation plants a seed of hope in someone’s life."),
    HEALING_HAND(11, 15, "🤲", "Healing Hand", "You're now a consistent force of healing and kindness."),
    VITAL_FLAME(16, 20, "🔥", "Vital Flame", "Your generosity keeps the flame of life burning bright."),
    LIFE_DEFENDER(21, 25, "🛡️", "Life Defender", "A true protector through repeated acts of compassion."),
    BLOOD_CHAMPION(26, 30, "🏅", "Blood Champion", "Your dedication makes you a true champion of humanity."),
    GUARDIAN_SOUL(31, 35, "🕊️", "Guardian Soul", "A soulful guardian spreading peace through giving."),
    BEACON_OF_LIFE(36, 40, "✨", "Beacon of Life", "You shine as a guiding light for those in darkness."),
    DONATION_ROYALTY(41, 45, "👑", "Donation Royalty", "Your legacy in donation is nothing short of royal."),
    ETERNAL_GIVER(46, 50, "🕊️🌍", "Eternal Giver", "Your endless giving echoes across generations."),
    DAWN_GIVER(51, 55, "🌄", "Dawn Giver", "A new dawn rises for many, thanks to your giving."),
    SOUL_HEALER(56, 60, "🦋", "Soul Healer", "Your kindness brings healing to both body and soul."),
    LIFEBLOOD_ORACLE(61, 65, "🔮", "Lifeblood Oracle", "A visionary in the journey of saving lives."),
    HOPE_BEARER(66, 70, "🕯️", "Hope Bearer", "Carrying the light of hope through every donation."),
    BLOOD_VOYAGER(71, 75, "🌌", "Blood Voyager", "Exploring the universe of humanity through giving."),
    CRIMSON_KNIGHT(76, 80, "⚔️", "Crimson Knight", "A warrior in the noble battle for life."),
    PULSE_DRAGON(81, 85, "🐉", "Pulse Dragon", "A legendary lifeforce protector with fierce compassion."),
    LIFE_ALCHEMIST(86, 90, "🌈", "Life Alchemist", "Turning drops of blood into miracles of life."),
    SACRED_VEIN(91, 95, "🔱", "Sacred Vein", "Honored and revered for your boundless generosity."),
    IMMORTAL_GIVER(96, Integer.MAX_VALUE, "👁️‍🗨️", "Immortal Giver", "Your legacy is immortalized in every life saved.");

    private final int minDonations;
    private final int maxDonations;
    private final String icon;
    private final String levelName;
    private final String description;

    BloodDonorLevel(int minDonations, int maxDonations, String icon, String levelName, String description) {
        this.minDonations = minDonations;
        this.maxDonations = maxDonations;
        this.icon = icon;
        this.levelName = levelName;
        this.description = description;
    }

    public int getMinDonations() {
        return minDonations;
    }

    public int getMaxDonations() {
        return maxDonations;
    }

    public String getIcon() {
        return icon;
    }

    public String getLevelName() {
        return levelName;
    }

    public String getDescription() {
        return description;
    }

    public static BloodDonorLevel getLevelByDonationCount(int donationCount) {
        for (BloodDonorLevel level : BloodDonorLevel.values()) {
            if (donationCount >= level.minDonations && donationCount <= level.maxDonations) {
                return level;
            }
        }
        return null;
    }

    public BloodDonorLevel getNextLevel() {
        BloodDonorLevel[] levels = BloodDonorLevel.values();
        int currentIndex = this.ordinal();
        if (currentIndex < levels.length - 1) {
            return levels[currentIndex + 1];
        }
        return null;
    }

    public int getDonationsToNextLevel(int currentDonations) {
        BloodDonorLevel nextLevel = getNextLevel();
        if (nextLevel != null) {
            return Math.max(0, nextLevel.getMinDonations() - currentDonations);
        }
        return 0;
    }

    public double getProgressPercentage(int currentDonations) {
        if (this == IMMORTAL_GIVER) {
            return 100.0;
        }
        int levelRange = maxDonations - minDonations + 1;
        int currentProgress = Math.min(currentDonations - minDonations + 1, levelRange);
        return (double) currentProgress / levelRange * 100.0;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d+ donations)", icon, levelName, minDonations);
    }
}
