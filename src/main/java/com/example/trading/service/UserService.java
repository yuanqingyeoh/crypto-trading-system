package com.example.trading.service;

import com.example.trading.model.Wallet;
import com.example.trading.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final WalletRepository walletRepository;

    public UserService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    /**
     * Retrieves the wallet balance for a specific user.
     * @param   userId the ID of the user
     * @return  an Optional containing the wallet if it exists, otherwise empty
     */
    public Optional<Wallet> getWallet(Long userId) {
        return walletRepository.findByUserUserId(userId);
    }
}
