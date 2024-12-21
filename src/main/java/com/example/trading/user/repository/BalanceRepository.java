package com.example.trading.user.repository;

import com.example.trading.user.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findByUserUserId(Long userId);
}
