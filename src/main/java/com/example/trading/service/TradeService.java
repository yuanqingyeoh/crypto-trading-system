package com.example.trading.service;

import com.example.trading.model.Trade;
import com.example.trading.repository.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getTradeHistory(Long userId) {
        return tradeRepository.findByUserUserId(userId);
    }


}
