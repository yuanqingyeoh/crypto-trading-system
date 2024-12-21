package com.example.trading.trade.resource;

import com.example.trading.trade.dto.MakeTradeDTO;
import com.example.trading.trade.model.Trade;
import com.example.trading.trade.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class TradeResource {

    private final TradeService tradeService;

    public TradeResource(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    /**
     * Endpoint to retrieve the user's wallet balance.
     * @param userId the ID of the user
     * @return ResponseEntity containing the wallet balance or a 404 error if the wallet doesn't exist
     */
    @GetMapping("/tradeHistory/{userId}")
    public ResponseEntity<List<Trade>> getTradeHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(tradeService.getTradeHistory(userId));
    }


    @PostMapping("/makeTrade")
    public ResponseEntity<Trade> makeTrade(MakeTradeDTO makeTradeDTO) {
        return ResponseEntity.ok(tradeService.makeTrade(makeTradeDTO));
    }


}
