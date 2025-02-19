package bumblebee.xchangepass.domain.wallet.dto.request;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

public record WalletTransferRequest(
        Long senderId,
        Long receiverId,
        String accountNumber,
        BigDecimal transferAmount,
        Currency fromCurrency,
        Currency toCurrency,
        LocalDateTime transferDatetime
) {
}
