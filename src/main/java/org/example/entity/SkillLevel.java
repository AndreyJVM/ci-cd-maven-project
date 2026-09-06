package org.example.entity;

public enum SkillLevel {
    ADVANCED("Продвинутый", 90, "bg-success"),
    PROFICIENT("Уверенный", 75, "bg-primary"),
    INTERMEDIATE("Базовый / В процессе", 50, "bg-info");

    private final String title;
    private final int percentage;
    private final String badgeColor;

    SkillLevel(String title, int percentage, String badgeColor) {
        this.title = title;
        this.percentage = percentage;
        this.badgeColor = badgeColor;
    }

    public String getTitle() {
        return title;
    }

    public int getPercentage() {
        return percentage;
    }

    public String getBadgeColor() {
        return badgeColor;
    }
}