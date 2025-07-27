package com.lcwd.user.service.controller;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User user1 = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable String userId)
    {
        User user = userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/{userId}")
//    @Retry(name = "ratingHotelRetry", fallbackMethod = "getUsersFallback")
    @RateLimiter(name = "ratingHotelRateLimiter", fallbackMethod = "getUsersFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId)
    {
        User user = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


    public ResponseEntity<User> getUsersFallback(String userId, Exception ex) {
        logger.error("Fallback is executed {}", ex.getMessage());

        User fallbackUser = User.builder()
                .name("Dummy")
                .email("nA1Ua@example.com")
                .about("This user is created dummy because the some service is down")
                .userId("1234768575")
                .build();

             return ResponseEntity.status(HttpStatus.OK).body(fallbackUser);

    }




    @GetMapping
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "getAllUsersFallback")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    public ResponseEntity<List<User>> getAllUsersFallback(Exception ex)
    {
        logger.error("Fallback is executed {}", ex.getMessage());

            User fallbackUser = User.builder()
                    .name("Dummy")
                    .email("nA1Ua@example.com")
                    .about("This user is created dummy because the some service is down")
                    .userId("1234768575")
                    .build();

            List<User> users = new ArrayList<>();
            users.add(fallbackUser);

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(users);
    }



}
