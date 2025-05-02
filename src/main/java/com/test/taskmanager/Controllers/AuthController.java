package com.test.taskmanager.Controllers;

import com.test.taskmanager.Config.JwtUtil;
import com.test.taskmanager.Dto.AuthRequest;
import com.test.taskmanager.Dto.AuthResponse;
import com.test.taskmanager.Model.User;
import com.test.taskmanager.Service.CostomUserDetailsService;
import com.test.taskmanager.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CostomUserDetailsService costomUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthController(CostomUserDetailsService costomUserDetailsService,
                          JwtUtil jwtUtil, UserService userService) {
        this.costomUserDetailsService = costomUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {

        try {

            final User user = userService.findByUserName(request.getEmail());

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }

            final String token = jwtUtil.generateToken(user);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getStackTrace().toString());
        }
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

