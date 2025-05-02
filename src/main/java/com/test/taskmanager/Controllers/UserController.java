package com.test.taskmanager.Controllers;

import com.test.taskmanager.Model.User;
import com.test.taskmanager.Service.CostomUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final CostomUserDetailsService costomUserDetailsService;

    public UserController(CostomUserDetailsService costomUserDetailsService) {
        this.costomUserDetailsService = costomUserDetailsService;
    }

    @PostMapping("/")
    public String registerUser(@RequestBody User test) {
        final UserDetails userDetails = costomUserDetailsService.loadUserByUsername("name");
        return "Welcome ";
    }
}
