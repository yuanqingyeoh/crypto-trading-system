package com.example.trading.trade.repository;

import com.example.trading.trade.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TradeRepository extends JpaRepository<Trade, Long> {

    List<Trade> findByUserUserId(Long userId);
}
