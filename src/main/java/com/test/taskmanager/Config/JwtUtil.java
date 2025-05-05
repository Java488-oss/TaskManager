package com.test.taskmanager.Config;

import com.test.taskmanager.Model.TokenStatus;
import com.test.taskmanager.Model.User;
import com.test.taskmanager.Service.CustomRedisTemplate;
import com.test.taskmanager.Service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.StreamSupport;

@Component
public class JwtUtil {
    private final SecretKey SECRET_KEY;
    private final String secret;
    private final UserService userService;
    private int timerForToken;
    private final CustomRedisTemplate customRedisTemplate;
    private String timerParam;

    public JwtUtil(@Value("${jwt.secret}") String secret, @Value("${jwt.timer}") int timerForToken,@Value("${jwt.timer.param}") String timerParam, UserService userService, CustomRedisTemplate customRedisTemplate) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.secret = secret;
        this.userService = userService;
        this.timerForToken = timerForToken;
        this.customRedisTemplate = customRedisTemplate;
        this.timerParam = timerParam;
    }

    public String generateToken(User user) {
        String jwtToken = Jwts.builder()
                .setSubject(user.getUserName())
                .claim("userId", user.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + getTimerParam())) // 1 день
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        customRedisTemplate.save(jwtToken, TokenStatus.ACTIVE);
        return jwtToken;
    }

    private long getTimerParam() {
        int value = Integer.parseInt(timerParam.split(":")[0]);
        TimeUnit timeUnit = TimeUnit.valueOf(timerParam.split(":")[1].toUpperCase());
        return timeUnit.toMillis(value);
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public <T> T extractParam(String paramName, String jwtToken, Class<T> clazz) {
        Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))).parseClaimsJws(jwtToken).getBody();
        return claims.get(paramName, clazz);
    }

    public boolean validateTokenWithUserDetails(String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    public User getUserFromToken(HttpServletRequest request) {
        // Получение заголовка Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Извлечение JWT-токена (удаляем префикс "Bearer ")
            String jwtToken = authHeader.substring(7);
            // Здесь можно обработать токен (например, валидация)
            if (validateToken(jwtToken)) {
                try {
                    //Тут разрешенная логика
                    Long userId = StreamSupport.stream(Collections.singleton(extractParam("userId", jwtToken, Long.class)).spliterator(), false)
                            .findFirst()
                            .orElse(null); // или throw / default value

                    if (userId == null) throw new IllegalArgumentException("userId must not be null");
                    Optional<User> user = userService.findById(userId);


                    return user.orElse(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
        return null;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        // Получение заголовка Authorization
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Извлечение JWT-токена (удаляем префикс "Bearer ")
            String jwtToken = authHeader.substring(7);
            // Здесь можно обработать токен (например, валидация)
            if (validateToken(jwtToken)) {
                return jwtToken;
            }
        }
        return null;
    }

    public boolean validateToken(String jwtToken) {
        try {
            if(!isTokenExpired(jwtToken)) {
                Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken);
                return true;
            }else {
                customRedisTemplate.remove(jwtToken);
                return false;
            }
        } catch (ExpiredJwtException e) {
            customRedisTemplate.remove(jwtToken);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expiration = Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}