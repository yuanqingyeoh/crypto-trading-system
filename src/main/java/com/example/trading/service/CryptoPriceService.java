package com.example.trading.service;

import com.example.trading.model.CryptoPrice;
import com.example.trading.repository.CryptoPriceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoPriceService {

    private final CryptoPriceRepository cryptoPriceRepository;

    public CryptoPriceService(CryptoPriceRepository cryptoPriceRepository) {
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    public List<CryptoPrice> getAllSymbol() {
        return cryptoPriceRepository.findAll();
    }

    public CryptoPrice getSymbol(String symbol) {
        return cryptoPriceRepository.findByCryptoPair(symbol).orElseThrow(EntityNotFoundException::new);
    }
}
