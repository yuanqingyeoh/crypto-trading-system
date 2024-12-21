package com.example.trading.price.dto;

import java.math.BigDecimal;
import java.util.List;

public class HuobiTickerPayload {
    private List<HuobiTicker> data;

    public List<HuobiTicker> getData() {
        return data;
    }

    public void setData(List<HuobiTicker> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HuobiTickerPayload{" +
                "data=" + data +
                '}';
    }

    public static class HuobiTicker{
        private String symbol;
        private BigDecimal open;
        private BigDecimal high;
        private BigDecimal low;
        private BigDecimal close;
        private BigDecimal amount;
        private BigDecimal vol;
        private BigDecimal count;
        private BigDecimal bid;
        private BigDecimal bidSize;
        private BigDecimal ask;
        private BigDecimal askSize;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public BigDecimal getOpen() {
            return open;
        }

        public void setOpen(BigDecimal open) {
            this.open = open;
        }

        public BigDecimal getHigh() {
            return high;
        }

        public void setHigh(BigDecimal high) {
            this.high = high;
        }

        public BigDecimal getLow() {
            return low;
        }

        public void setLow(BigDecimal low) {
            this.low = low;
        }

        public BigDecimal getClose() {
            return close;
        }

        public void setClose(BigDecimal close) {
            this.close = close;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public BigDecimal getVol() {
            return vol;
        }

        public void setVol(BigDecimal vol) {
            this.vol = vol;
        }

        public BigDecimal getCount() {
            return count;
        }

        public void setCount(BigDecimal count) {
            this.count = count;
        }

        public BigDecimal getBid() {
            return bid;
        }

        public void setBid(BigDecimal bid) {
            this.bid = bid;
        }

        public BigDecimal getBidSize() {
            return bidSize;
        }

        public void setBidSize(BigDecimal bidSize) {
            this.bidSize = bidSize;
        }

        public BigDecimal getAsk() {
            return ask;
        }

        public void setAsk(BigDecimal ask) {
            this.ask = ask;
        }

        public BigDecimal getAskSize() {
            return askSize;
        }

        public void setAskSize(BigDecimal askSize) {
            this.askSize = askSize;
        }

        @Override
        public String toString() {
            return "HuobiTicker{" +
                    "symbol='" + symbol + '\'' +
                    ", open=" + open +
                    ", high=" + high +
                    ", low=" + low +
                    ", close=" + close +
                    ", amount=" + amount +
                    ", vol=" + vol +
                    ", count=" + count +
                    ", bid=" + bid +
                    ", bidSize=" + bidSize +
                    ", ask=" + ask +
                    ", askSize=" + askSize +
                    '}';
        }
    }
}
