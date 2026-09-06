package org.example.entity;

public enum SkillCategory {
    QA_AUTOMATION("Тестирование и автоматизация", "fas fa-vial"),
    DEVOPS_CICD("DevOps & CI/CD", "fas fa-infinity"),
    LINUX_INFRA("Linux, Сети и Администрирование", "fas fa-server"),
    BACKEND_DB("Backend, Java & Базы данных", "fas fa-database");

    private final String displayName;
    private final String iconClass;

    SkillCategory(String displayName, String iconClass) {
        this.displayName = displayName;
        this.iconClass = iconClass;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getIconClass() {
        return iconClass;
    }
}