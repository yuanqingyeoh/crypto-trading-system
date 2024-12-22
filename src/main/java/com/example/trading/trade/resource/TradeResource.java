package com.example.trading.trade.resource;

import com.example.trading.trade.dto.MakeTradeDTO;
import com.example.trading.trade.model.Trade;
import com.example.trading.trade.service.TradeService;
import com.example.trading.util.TradeException;
import io.swagger.v3.oas.annotations.Operation;
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
     * Endpoint to retrieve the user's trade history.
     *
     * @param userId the ID of the user
     * @return ResponseEntity containing the user's trade history
     */
    @Operation(summary = "Get user trade history")
    @GetMapping("/tradeHistory/{userId}")
    public ResponseEntity<List<Trade>> getTradeHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(tradeService.getTradeHistory(userId));
    }

    /**
     * Endpoint to make a trade (BUY/SELL)
     *
     * @param makeTradeDTO      DTO containing trade information
     * @return                  ResponseEntity containing the trade made
     */
    @Operation(summary = "Make trade")
    @PostMapping("/makeTrade")
    public ResponseEntity<Trade> makeTrade(MakeTradeDTO makeTradeDTO) throws TradeException {
        return ResponseEntity.ok(tradeService.makeTrade(makeTradeDTO));
    }


}
