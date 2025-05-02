package com.test.taskmanager.Initializer;

import com.test.taskmanager.Model.Role;
import com.test.taskmanager.Model.User;
import com.test.taskmanager.Model.UserStatus;
import com.test.taskmanager.Repository.RoleRepository;
import com.test.taskmanager.Repository.UserRepository;
import com.test.taskmanager.Repository.UserStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserStatusRepository userStatusRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DataInitializer(UserStatusRepository userStatusRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.userStatusRepository = userStatusRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        if (userStatusRepository.count() == 0) {
            userStatusRepository.saveAll(List.of(
                    new UserStatus(1L, "Active"),
                    new UserStatus(2L, "Deleted"),
                    new UserStatus(3L, "Blocked"),
                    new UserStatus(4L, "Frozen")
            ));
        }

        if(roleRepository.count() == 0){
            roleRepository.saveAll(List.of(
                    new Role(1L, "Admin"),
                    new Role(2L, "User")
            ));
        }

        if(userRepository.count() == 0){
            userRepository.saveAll(List.of(
               new User("Email", "Name", "Password", "FirstName", "LastName", new Role(1L), new UserStatus(1L))
            ));
        }
    }
}
