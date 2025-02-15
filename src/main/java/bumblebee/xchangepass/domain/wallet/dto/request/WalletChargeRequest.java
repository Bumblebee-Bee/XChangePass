package bumblebee.xchangepass.domain.wallet.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record WalletChargeRequest(
        Long userId,
        BigDecimal chargeAmount,
        Currency fromCurrency,
        Currency toCurrency,
        LocalDateTime chargeDatetime
) {
}
