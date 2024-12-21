package com.example.trading.repository;

import com.example.trading.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByUserUserId(Long userId);
}
