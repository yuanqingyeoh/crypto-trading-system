package com.example.trading.price.service;

import com.example.trading.price.model.CryptoPrice;
import com.example.trading.price.repository.CryptoPriceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CryptoPriceService {

    private final CryptoPriceRepository cryptoPriceRepository;

    public CryptoPriceService(CryptoPriceRepository cryptoPriceRepository) {
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    /**
     * Get the price for all symbols
     *
     * @return  List of price for all symbols
     */
    public List<CryptoPrice> getAllSymbol() {
        return cryptoPriceRepository.findAll();
    }

    /**
     * Get the price for specified symbol
     * @param symbol    symbol to search
     * @return          Price for the symbol else throw error
     */
    public CryptoPrice getSymbol(String symbol) {
        return cryptoPriceRepository.findBySymbol(symbol).orElseThrow(() -> new EntityNotFoundException("Price not found for the symbol."));
    }
}
