package com.test.taskmanager.Repository;

import com.test.taskmanager.Model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
    UserStatus findByUserStatusId(Long userStatusId);

}
