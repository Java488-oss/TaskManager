package com.test.taskmanager.Model;

public enum TokenStatus {
    ACTIVE("Активен"),
    REVOKED("Отозван"),
    EXPIRED("Истёк"),
    BLOCKED("Заблокирован");

    private final String description;

    TokenStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
