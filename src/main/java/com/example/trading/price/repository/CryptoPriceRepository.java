package com.example.trading.price.repository;

import com.example.trading.price.model.CryptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, Long> {

    List<CryptoPrice> findBySymbolIn(List<String> symbols);

    Optional<CryptoPrice> findBySymbol(String symbol);
}
