package com.test.taskmanager.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserStatus {

    @Id
    private Long userStatusId;

    private String userStatusName;

    // Конструктор с двумя параметрами
    public UserStatus(Long userStatusId, String userStatusName) {
        this.userStatusId = userStatusId;
        this.userStatusName = userStatusName;
    }

    // Конструктор с одним параметром (если нужен)
    public UserStatus(Long userStatusId) {
        this.userStatusId = userStatusId;
    }

    // Конструктор по умолчанию (обязательно нужен для JPA)
    public UserStatus() {
    }

    // Геттеры и сеттеры
    public Long getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(Long userStatusId) {
        this.userStatusId = userStatusId;
    }

    public String getUserStatusName() {
        return userStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        this.userStatusName = userStatusName;
    }
}

