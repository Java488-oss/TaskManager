package com.test.taskmanager.Repository;

import com.test.taskmanager.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleId(Long roleId);
}
