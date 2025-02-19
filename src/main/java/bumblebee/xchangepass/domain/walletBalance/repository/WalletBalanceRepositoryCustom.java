package bumblebee.xchangepass.domain.walletBalance.repository;

import bumblebee.xchangepass.domain.walletBalance.entity.WalletBalance;

import java.util.Currency;
import java.util.List;

public interface WalletBalanceRepositoryCustom {

    List<WalletBalance> findByWalletId(Long walletId);
    WalletBalance findByWalletIdAndCurrency(Long walletId, Currency currency);
}
