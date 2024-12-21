package com.example.trading.util;

import com.example.trading.user.model.Balance;
import com.example.trading.user.model.User;
import com.example.trading.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * This class is just a class to generate testing data on startup.
 */

@Service
public class CreateUserTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateUserTask.class);
    private final UserService userService;

    public CreateUserTask(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        User newUser = new User();
        newUser.setEmail("test@gmail.com");
        newUser.setFullName("Test user");
        newUser.setUsername("test_username");
        newUser.setIsActive(true);
        newUser.setPassword("password");

        User saved = userService.saveUser(newUser);
        LOGGER.info("Saved User: {}", saved);

        Balance balance = new Balance();
        balance.setCoin("USDT");
        balance.setBalance(new BigDecimal(50000));
        balance.setUser(saved);

        userService.saveBalances(Collections.singletonList(balance));
    }
}
