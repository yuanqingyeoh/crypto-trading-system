package com.example.trading.trade.service;

import com.example.trading.price.model.CryptoPrice;
import com.example.trading.trade.dto.MakeTradeDTO;
import com.example.trading.trade.model.Trade;
import com.example.trading.user.model.Balance;
import com.example.trading.price.service.CryptoPriceService;
import com.example.trading.user.model.User;
import com.example.trading.trade.repository.TradeRepository;
import com.example.trading.user.service.UserService;
import com.example.trading.util.TradeException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.example.trading.util.Constant.ACTION_BUY;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final UserService userService;
    private final CryptoPriceService cryptoPriceService;

    public TradeService(TradeRepository tradeRepository,
                        UserService userService,
                        CryptoPriceService cryptoPriceService) {
        this.tradeRepository = tradeRepository;
        this.userService = userService;
        this.cryptoPriceService = cryptoPriceService;
    }

    /**
     * Get trade history by userId
     *
     * @param userId    The userId to search
     * @return          List of trade made by the user
     */
    public List<Trade> getTradeHistory(Long userId) {
        return tradeRepository.findByUserUserId(userId);
    }


    /**
     * Make trade
     *
     * @param makeTradeDTO      DTO containing trade information
     * @return                  Trade made
     */
    public Trade makeTrade(MakeTradeDTO makeTradeDTO) throws TradeException {
        Optional<User> userOpt = this.userService.getUser(makeTradeDTO.getUserId());

        if (userOpt.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }
        Trade trade = new Trade();
        trade.setUser(userOpt.get());
        trade.setTradeType(makeTradeDTO.getAction());
        trade.setQuantity(makeTradeDTO.getQuantity());
        trade.setCryptoPair(makeTradeDTO.getSymbol());
        trade.setTimestamp(LocalDateTime.now());

        String pair1 = makeTradeDTO.getSymbol().substring(0, 3).toUpperCase();
        String pair2 = makeTradeDTO.getSymbol().substring(3).toUpperCase();

        validateTradePair(pair1, pair2);

        Set<Balance> balances = userOpt.get().getBalances();

        CryptoPrice price = cryptoPriceService.getSymbol(makeTradeDTO.getSymbol());

        // check balance
        Optional<Balance> pair1BalanceOpt = balances.stream().filter(b -> pair1.equalsIgnoreCase(b.getCoin())).findAny();
        Optional<Balance> pair2BalanceOpt = balances.stream().filter(b -> pair2.equalsIgnoreCase(b.getCoin())).findAny();


        if (makeTradeDTO.getAction().equalsIgnoreCase(ACTION_BUY)) {

            if (pair2BalanceOpt.isEmpty()){
                throw new TradeException("Insufficient " + pair2 + " Balance");
            }

            BigDecimal total = price.getAskPrice().multiply(makeTradeDTO.getQuantity());

            if (pair2BalanceOpt.get().getBalance().compareTo(total) < 0) {
                // insufficient balance
                throw new TradeException("Insufficient " + pair2 + " Balance");
            }

            Balance pair1Balance = new Balance();
            Balance pair2Balance = pair2BalanceOpt.get();

            pair2Balance.setBalance(pair2Balance.getBalance().subtract(total));

            if (pair1BalanceOpt.isPresent()) {
                pair1Balance = pair1BalanceOpt.get();
                pair1Balance.setBalance(pair1BalanceOpt.get().getBalance().add(makeTradeDTO.getQuantity()));
            } else {
                pair1Balance.setCoin(pair1);
                pair1Balance.setBalance(makeTradeDTO.getQuantity());
                pair1Balance.setUser(userOpt.get());
            }

            List<Balance> toSave = new ArrayList<>();
            toSave.add(pair1Balance);
            toSave.add(pair2Balance);
            userService.saveBalances(toSave);

            trade.setPrice(price.getAskPrice());

            return tradeRepository.save(trade);



        } else {

            if (pair1BalanceOpt.isEmpty()){
                throw new TradeException("Insufficient " + pair1 + " Balance");
            }

            BigDecimal total = price.getBidPrice().multiply(makeTradeDTO.getQuantity());

            if (pair1BalanceOpt.get().getBalance().compareTo(makeTradeDTO.getQuantity()) < 0) {
                // insufficient balance
                throw new TradeException("Insufficient " + pair1 + " Balance");
            }

            Balance pair1Balance = pair1BalanceOpt.get();
            Balance pair2Balance = new Balance();

            pair1Balance.setBalance(pair1Balance.getBalance().subtract(makeTradeDTO.getQuantity()));

            if (pair2BalanceOpt.isPresent()) {
                pair2Balance = pair2BalanceOpt.get();
                pair2Balance.setBalance(pair2BalanceOpt.get().getBalance().add(total));
            } else {
                pair2Balance.setCoin(pair2);
                pair2Balance.setBalance(makeTradeDTO.getQuantity());
                pair2Balance.setUser(userOpt.get());
            }

            List<Balance> toSave = new ArrayList<>();
            toSave.add(pair1Balance);
            toSave.add(pair2Balance);
            userService.saveBalances(toSave);

            trade.setPrice(price.getBidPrice());

            return tradeRepository.save(trade);

        }
    }

    private void validateTradePair(String pair1, String pair2) throws TradeException {
        if (!"BTC".equalsIgnoreCase(pair1) || !"ETH".equalsIgnoreCase(pair1) || !"USDT".equalsIgnoreCase(pair2)) {
            throw new TradeException("Only BTCUSDT and ETHUSDT trade pair supported now.");
        }
    }


}
