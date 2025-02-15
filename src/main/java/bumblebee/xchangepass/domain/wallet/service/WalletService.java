package bumblebee.xchangepass.domain.wallet.service;

import bumblebee.xchangepass.domain.wallet.entity.Wallet;
import bumblebee.xchangepass.domain.walletBalance.entity.WalletBalance;
import bumblebee.xchangepass.domain.walletBalance.repository.WalletBalanceRepository;
import bumblebee.xchangepass.domain.wallet.dto.request.WalletChargeRequest;
import bumblebee.xchangepass.domain.wallet.repository.WalletRepository;
import bumblebee.xchangepass.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    public final WalletRepository walletRepository;
    public final WalletBalanceRepository balanceRepository;

    @Transactional
    public void charge(WalletChargeRequest request) {
        if (!request.toCurrency().equals(request.fromCurrency())) {
            //환전

        }

        Wallet wallet = walletRepository.findByUserId(request.userId());

        List<WalletBalance> balanceList = balanceRepository.findByWalletId(wallet.walletId);
        for (WalletBalance balance : balanceList) {
            if (balance.currency.equals(request.toCurrency())) {
                WalletBalance findBalance = balanceRepository.findById(balance.getBalanceId())
                        .orElseThrow(ErrorCode.BALANCE_NOT_FOUND::commonException);

                findBalance.changeBalance(request.chargeAmount());
                balanceRepository.save(findBalance);
                break;
            }
        }


    }
}
