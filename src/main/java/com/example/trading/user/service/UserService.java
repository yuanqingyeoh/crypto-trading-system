package com.example.trading.user.service;

import com.example.trading.user.model.Balance;
import com.example.trading.user.model.SaveUserDTO;
import com.example.trading.user.model.User;
import com.example.trading.user.repository.UserRepository;
import com.example.trading.user.repository.BalanceRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final BalanceRepository balanceRepository;
    private final UserRepository userRepository;

    public UserService(BalanceRepository balanceRepository,
                       UserRepository userRepository) {
        this.balanceRepository = balanceRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the wallet balance for a specific user.
     * @param   userId the ID of the user
     * @return  an Optional containing the wallet if it exists, otherwise empty
     */
    public Optional<User> getUser(Long userId) {
        return userRepository.findById(userId);
    }

    public User saveUser(SaveUserDTO saveUserDTO){
        Optional<User> userOpt = userRepository.findByUsername(saveUserDTO.getUsername());
        User toSave = new User();
        toSave.setUsername(saveUserDTO.getUsername());

        if (userOpt.isPresent()) {
            toSave = userOpt.get();
        }

        if (StringUtils.isNotBlank(saveUserDTO.getEmail())) {
            toSave.setEmail(saveUserDTO.getEmail());
        }

        if (StringUtils.isNotBlank(saveUserDTO.getPassword())) {
            toSave.setPassword(saveUserDTO.getPassword());
        }

        if (StringUtils.isNotBlank(saveUserDTO.getFullName())) {
            toSave.setFullName(saveUserDTO.getFullName());
        }

        toSave.setIsActive(saveUserDTO.getActive());
        toSave.setUpdatedTimestamp(LocalDateTime.now());

        boolean isInsert = false;
        if (toSave.getUserId() == null) {
            isInsert = true;
        }

        toSave = this.userRepository.save(toSave);

        // Special case new user has 50000 USDT
        if (isInsert) {
            Balance balance = new Balance();
            balance.setCoin("USDT");
            balance.setBalance(new BigDecimal(50000));
            balance.setUser(toSave);

            saveBalances(Collections.singletonList(balance));
        }

        return toSave;
    }

    public List<Balance> saveBalances(List<Balance> balances) {
        return this.balanceRepository.saveAll(balances);
    }
}
