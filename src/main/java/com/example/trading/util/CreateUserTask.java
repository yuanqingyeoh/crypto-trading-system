package com.example.trading.util;

import com.example.trading.user.model.SaveUserDTO;
import com.example.trading.user.model.User;
import com.example.trading.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;


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
        SaveUserDTO saveUserDTO = new SaveUserDTO(
                "test_username",
                "test@gmail.com",
                "Test user",
                "password",
                true);

        User saved = userService.saveUser(saveUserDTO);
        LOGGER.info("Saved User: {}", saved);
    }
}
