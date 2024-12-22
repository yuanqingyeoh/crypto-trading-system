package com.example.trading.price.service;

import com.example.trading.price.model.CryptoPrice;
import com.example.trading.price.dto.BinanceTickerPayload;
import com.example.trading.price.dto.HuobiTickerPayload;
import com.example.trading.price.repository.CryptoPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CryptoPriceTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoPriceTask.class);
    private static final String BINANCE_URL = "https://api.binance.com/api/v3/";
    private static final String HUOBI_URL = "https://api.huobi.pro/";

    private final List<String> SYMBOL_TO_PROCESS = Arrays.stream(new String[] {"ETHUSDT", "BTCUSDT"}).toList();

    private final RestClient binanceRestClient;
    private final RestClient huobiRestClient;

    private final CryptoPriceRepository cryptoPriceRepository;

    public CryptoPriceTask(RestClient.Builder restClientBuilder,
                           CryptoPriceRepository cryptoPriceRepository) {
        this.binanceRestClient = restClientBuilder.baseUrl(BINANCE_URL).build();
        this.huobiRestClient = restClientBuilder.baseUrl(HUOBI_URL).build();
        this.cryptoPriceRepository = cryptoPriceRepository;
    }

    /**
     * Get ticker data from Binance
     * @return  ticker data payload from Binance
     */
    private List<BinanceTickerPayload> binanceCall() {
        List<BinanceTickerPayload> response = Arrays.stream(this.binanceRestClient.get().uri("ticker/bookTicker").retrieve().body(BinanceTickerPayload[].class)).toList();
        LOGGER.trace(response.toString());
        return response;
    }

    /**
     * Get ticker data from Huobi
     * @return  ticker data payload from Huobi
     */
    private HuobiTickerPayload huobiCall() {
        HuobiTickerPayload response = this.huobiRestClient.get().uri("market/tickers").retrieve().body(HuobiTickerPayload.class);
        LOGGER.trace(response.toString());
        return response;
    }

    /**
     * Scheduled task that execute every 10 seconds to fetch data from Binance and Huobi to aggregate latest price
     */
    @Scheduled(fixedRate = 10000)
    public void fetchData() {
        List<BinanceTickerPayload> binanceTickers = binanceCall();

        HuobiTickerPayload huobiTickerPayload = huobiCall();


        Set<String> uniqueSymbols = binanceTickers.stream().map(BinanceTickerPayload::getSymbol).map(String::toUpperCase).collect(Collectors.toSet());
        uniqueSymbols.addAll(huobiTickerPayload.getData().stream().map(HuobiTickerPayload.HuobiTicker::getSymbol).map(String::toUpperCase).collect(Collectors.toSet()));

        List<CryptoPrice> cryptoPrices = cryptoPriceRepository.findBySymbolIn(uniqueSymbols.stream().toList());
        List<CryptoPrice> toSave = new ArrayList<>();

        uniqueSymbols.forEach(symbol -> {
            Optional<BinanceTickerPayload> binanceOpt = binanceTickers.stream().filter(binanceTick -> symbol.equalsIgnoreCase(binanceTick.getSymbol())).findFirst();

            Optional<HuobiTickerPayload.HuobiTicker> huobiOpt = huobiTickerPayload.getData().stream().filter(huobiTick -> symbol.equalsIgnoreCase(huobiTick.getSymbol())).findFirst();


            CryptoPrice toUpdate = new CryptoPrice();
            toUpdate.setSymbol(symbol);

            Optional<CryptoPrice> existing = cryptoPrices.stream().filter(cryptoPrice -> symbol.equalsIgnoreCase(cryptoPrice.getSymbol())).findFirst();
            if (existing.isPresent()) {
                toUpdate = existing.get();
            }

            if (binanceOpt.isPresent() && huobiOpt.isPresent()) {
                BinanceTickerPayload currentBinance = binanceOpt.get();
                HuobiTickerPayload.HuobiTicker currentHuobi = huobiOpt.get();

                // Lower ask price is used
                if (currentBinance.getAskPrice().compareTo(currentHuobi.getAsk()) > 0) {
                    toUpdate.setAskPrice(currentHuobi.getAsk());
                } else {
                    toUpdate.setAskPrice(currentBinance.getAskPrice());
                }

                // Higher bid price is used
                if (currentBinance.getBidPrice().compareTo(currentHuobi.getBid()) > 0) {
                    toUpdate.setBidPrice(currentBinance.getBidPrice());
                } else {
                    toUpdate.setBidPrice(currentHuobi.getBid());
                }
                toUpdate.setLastUpdated(LocalDateTime.now());
                toSave.add(toUpdate);

            } else if (binanceOpt.isPresent()) {
                BinanceTickerPayload current = binanceOpt.get();
                toUpdate.setAskPrice(current.getAskPrice());
                toUpdate.setBidPrice(current.getBidPrice());
                toUpdate.setLastUpdated(LocalDateTime.now());
                toSave.add(toUpdate);

            } else if (huobiOpt.isPresent()) {
                HuobiTickerPayload.HuobiTicker current = huobiOpt.get();
                toUpdate.setAskPrice(current.getAsk());
                toUpdate.setBidPrice(current.getBid());
                toUpdate.setLastUpdated(LocalDateTime.now());
                toSave.add(toUpdate);
            }
        });

        cryptoPriceRepository.saveAll(toSave);
    }
}
