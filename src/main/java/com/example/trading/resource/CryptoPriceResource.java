package com.example.trading.resource;

import com.example.trading.model.CryptoPrice;
import com.example.trading.service.CryptoPriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cryptoPrice")
public class CryptoPriceResource {

    private final CryptoPriceService cryptoPriceService;

    public CryptoPriceResource(CryptoPriceService cryptoPriceService) {
        this.cryptoPriceService = cryptoPriceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<CryptoPrice>> getAllSymbol() {
        return ResponseEntity.ok(cryptoPriceService.getAllSymbol());
    }

    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<CryptoPrice> getSymbol(@PathVariable("symbol") String symbol) {
        return ResponseEntity.ok(cryptoPriceService.getSymbol(symbol));
    }
}
