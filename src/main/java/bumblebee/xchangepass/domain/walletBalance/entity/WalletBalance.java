package bumblebee.xchangepass.domain.walletBalance.entity;

import bumblebee.xchangepass.domain.wallet.entity.Wallet;
import bumblebee.xchangepass.global.converter.CurrencyConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "balance")
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class WalletBalance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id", nullable = false)
    public Long balanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    public Wallet wallet;

    @Convert(converter = CurrencyConverter.class)
    public Currency currency;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @CreatedDate
    public LocalDateTime currencyCreatedAt;

    @LastModifiedDate
    public LocalDateTime currencyModifiedAt;

    public void addBalance(BigDecimal balance) {
        this.balance = this.balance.add(balance);
    }

    public void subtractBalance(BigDecimal balance) {
        this.balance = this.balance.subtract(balance);
    }
}