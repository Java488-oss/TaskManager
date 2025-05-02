package com.test.taskmanager.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserStatus {
    @Id
    private Long UserStatusId;
    private String UserStatusName;

    public UserStatus(Long userStatusId, String userStatusName) {
        UserStatusId = userStatusId;
        UserStatusName = userStatusName;
    }
    public UserStatus(Long userStatusId) {
        UserStatusId = userStatusId;
    }

    public UserStatus() {
    }

    public Long getUserStatusId() {
        return UserStatusId;
    }

    public void setUserStatusId(Long userStatusId) {
        UserStatusId = userStatusId;
    }

    public String getUserStatusName() {
        return UserStatusName;
    }

    public void setUserStatusName(String userStatusName) {
        UserStatusName = userStatusName;
    }
}
