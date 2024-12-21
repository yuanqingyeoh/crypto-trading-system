package com.example.trading.user.service;

import com.example.trading.user.model.Balance;
import com.example.trading.user.model.User;
import com.example.trading.user.repository.UserRepository;
import com.example.trading.user.repository.BalanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public User saveUser(User user){
        user.setUpdatedTimestamp(LocalDateTime.now());
        return this.userRepository.save(user);
    }

    public List<Balance> saveBalances(List<Balance> balances) {
        return this.balanceRepository.saveAll(balances);
    }
}
