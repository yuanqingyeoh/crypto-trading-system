package com.example.trading.price.resource;

import com.example.trading.price.model.CryptoPrice;
import com.example.trading.price.service.CryptoPriceService;
import io.swagger.v3.oas.annotations.Operation;
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

    /**
     * Get the price for all symbols
     *
     * @return  Response entity with list of price for all symbols
     */
    @Operation(summary = "Get price for all symbols")
    @GetMapping("/all")
    public ResponseEntity<List<CryptoPrice>> getAllSymbol() {
        return ResponseEntity.ok(cryptoPriceService.getAllSymbol());
    }

    /**
     * Get the price for specified symbol
     * @param symbol    symbol to search
     * @return          Response entity with price for the symbol else return error
     */
    @Operation(summary = "Get price by symbol")
    @GetMapping("/symbol/{symbol}")
    public ResponseEntity<CryptoPrice> getSymbol(@PathVariable("symbol") String symbol) {
        return ResponseEntity.ok(cryptoPriceService.getSymbol(symbol));
    }
}
