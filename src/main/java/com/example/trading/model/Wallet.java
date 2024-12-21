package com.example.trading.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_WALLET")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private Double usdtBalance = 50000.0; // Default balance

    @Column(nullable = false)
    private Double btcBalance = 0.0;

    @Column(nullable = false)
    private Double ethBalance = 0.0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getUsdtBalance() {
        return usdtBalance;
    }

    public void setUsdtBalance(Double usdtBalance) {
        this.usdtBalance = usdtBalance;
    }

    public Double getBtcBalance() {
        return btcBalance;
    }

    public void setBtcBalance(Double btcBalance) {
        this.btcBalance = btcBalance;
    }

    public Double getEthBalance() {
        return ethBalance;
    }

    public void setEthBalance(Double ethBalance) {
        this.ethBalance = ethBalance;
    }
}
