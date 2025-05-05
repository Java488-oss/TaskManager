package com.test.taskmanager.Controllers;

import com.test.taskmanager.Config.JwtUtil;
import com.test.taskmanager.Model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final JwtUtil jwtUtil;

    public UserController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/testtoken")
    public ResponseEntity<?> handlePostRequest(HttpServletRequest request, @RequestBody String body) {
        User user = jwtUtil.getUserFromToken(request);

        if (user != null) {

            return ResponseEntity.status(HttpStatus.OK).body("Valid token "+ user.getRole().getRoleName() + " body "+body);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization header is missing or invalid");

    }
}
