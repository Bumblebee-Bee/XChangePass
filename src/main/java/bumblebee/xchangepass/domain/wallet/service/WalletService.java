package bumblebee.xchangepass.domain.wallet.service;

import bumblebee.xchangepass.domain.wallet.dto.request.WalletChargeRequest;
import bumblebee.xchangepass.domain.wallet.dto.request.WalletGetRequest;
import bumblebee.xchangepass.domain.wallet.dto.request.WalletTransferRequest;
import bumblebee.xchangepass.domain.wallet.dto.response.WalletBalanceResponse;
import bumblebee.xchangepass.domain.wallet.dto.response.WalletTransactionResponse;
import bumblebee.xchangepass.domain.wallet.entity.Wallet;
import bumblebee.xchangepass.domain.wallet.repository.WalletRepository;
import bumblebee.xchangepass.domain.walletBalance.entity.WalletBalance;
import bumblebee.xchangepass.domain.walletBalance.repository.WalletBalanceRepository;
import bumblebee.xchangepass.global.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletService {

    public final WalletRepository walletRepository;
    public final WalletBalanceRepository balanceRepository;

    @Transactional
    public List<WalletTransactionResponse> transaction(WalletGetRequest request) {


        return null;
    }


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

                findBalance.addBalance(request.chargeAmount());
                balanceRepository.save(findBalance);
                break;
            }
        }
    }

    @Transactional
    public void transfer(WalletTransferRequest request) {
        Wallet fromWallet = walletRepository.findByUserId(request.senderId());
        Wallet toWallet = walletRepository.findByUserId(request.receiverId());

        WalletBalance fromBalance = balanceRepository.findByWalletIdAndCurrency(fromWallet.walletId, request.fromCurrency());
        WalletBalance toBalance = balanceRepository.findByWalletIdAndCurrency(toWallet.walletId, request.toCurrency());

        BigDecimal transferAmount = request.transferAmount();

        if ((transferAmount.compareTo(fromBalance.getBalance())) > 0) {
            throw ErrorCode.BALANCE_NOT_AVAILABLE.commonException();
        }

        fromBalance.subtractBalance(transferAmount);
        balanceRepository.save(fromBalance);

        if (!request.fromCurrency().equals(request.toCurrency())) {
            //환전

        }


        toBalance.addBalance(transferAmount);
        balanceRepository.save(toBalance);

    }

    private void withdraw(WalletBalance balance, BigDecimal transferAmount) {

    }


    private void userTransfer(Long receiverId, BigDecimal transferAmount, Currency currency) {

    }

    @Transactional
    public List<WalletBalanceResponse> balance(WalletGetRequest request) {
        Wallet wallet = walletRepository.findByUserId(request.userId());

        List<WalletBalance> balanceList = balanceRepository.findByWalletId(wallet.walletId);

        return balanceList
                .stream()
                .map(balance -> new WalletBalanceResponse(
                        balance.currency.getCurrencyCode(),
                        balance.getBalance()
                ))
                .toList();
    }


}
