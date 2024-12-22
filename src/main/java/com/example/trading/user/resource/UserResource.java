package com.example.trading.user.resource;

import com.example.trading.user.model.SaveUserDTO;
import com.example.trading.user.model.User;
import com.example.trading.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserResource {
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint to retrieve the user's info.
     * @param userId the ID of the user
     * @return ResponseEntity containing the user info
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getuser(@PathVariable Long userId) {
        return userService.getUser(userId)
                .map(ResponseEntity::ok)
                .orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/saveUser")
    public ResponseEntity<User> saveUser(SaveUserDTO saveUserDTO) {
        return ResponseEntity.ok(userService.saveUser(saveUserDTO));
    }
}
