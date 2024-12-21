package com.example.trading.resource;

import com.example.trading.model.Wallet;
import com.example.trading.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
     * Endpoint to retrieve the user's wallet balance.
     * @param userId the ID of the user
     * @return ResponseEntity containing the wallet balance or a 404 error if the wallet doesn't exist
     */
    @GetMapping("/wallet/{userId}")
    public ResponseEntity<Wallet> getWalletBalance(@PathVariable Long userId) {
        return userService.getWallet(userId)
                .map(wallet -> ResponseEntity.ok(wallet))
                .orElseThrow(EntityNotFoundException::new);
    }
}
