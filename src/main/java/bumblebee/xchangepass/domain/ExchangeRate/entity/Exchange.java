package bumblebee.xchangepass.domain.ExchangeRate.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Exchange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 기본 키

    @Column(nullable = false, unique = true)
    private String currency; // 통화 코드 (USD, EUR)

    @Column(nullable = false)
    private String currencyName; // 국가/통화명

    @Setter
    @Column(nullable = false, precision = 18, scale = 6)
    private String rate; // 매매 기준율

    @Setter
    @Column(nullable = false)
    private LocalDateTime updatedAt; // 갱신 시간

    // DTO → Entity 변환을 위한 생성자

    @Builder
    public Exchange(String currency, String currencyName, String rate) {
        this.currency = currency;
        this.currencyName = currencyName;
        this.rate = rate;
        this.updatedAt = LocalDateTime.now();
    }

}
