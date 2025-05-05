package com.test.taskmanager.Controllers;

import com.test.taskmanager.Config.JwtUtil;
import com.test.taskmanager.Dto.AuthRequest;
import com.test.taskmanager.Dto.AuthResponse;
import com.test.taskmanager.Model.TokenStatus;
import com.test.taskmanager.Model.User;
import com.test.taskmanager.Service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CostomUserDetailsService costomUserDetailsService;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserStatusService userStatusService;
    private final CustomRedisTemplate customRedisTemplate;


    public AuthController(CostomUserDetailsService costomUserDetailsService,
                          JwtUtil jwtUtil, UserService userService, RoleService roleService, UserStatusService userStatusService, CustomRedisTemplate customRedisTemplate) {
        this.costomUserDetailsService = costomUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.roleService = roleService;
        this.userStatusService = userStatusService;
        this.customRedisTemplate = customRedisTemplate;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request) {

        try {

            final User user = userService.findByEmail(request.getEmail());

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

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody User user) {

        try {

            User emailIsExists = userService.findByEmail(user.getEmail());

            if(emailIsExists==null){

                user.setPassword(passwordEncoder.encode(user.getPassword()));

                userService.save(user);

                final String token = jwtUtil.generateToken(user);
                return ResponseEntity.ok(new AuthResponse(token));
            }

            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getStackTrace().toString());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            User user = jwtUtil.getUserFromToken(request);
            String jwtToken = jwtUtil.getTokenFromRequest(request);

            //customRedisTemplate.saveFromTtl(jwtToken, TokenStatus.ACTIVE);
            customRedisTemplate.getValue(jwtToken);


            return ResponseEntity.status(HttpStatus.OK).body("Successfully logged out");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getStackTrace().toString());
        }
    }

}

