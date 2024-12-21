package com.example.trading.repository;

import com.example.trading.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {

    List<CryptoPrice> findByCryptoPairIn(List<String> symbols);

    Optional<CryptoPrice> findByCryptoPair(String symbol);
}
