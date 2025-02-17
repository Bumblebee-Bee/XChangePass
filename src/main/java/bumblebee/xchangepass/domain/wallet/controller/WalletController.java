package bumblebee.xchangepass.domain.wallet.controller;

import bumblebee.xchangepass.domain.wallet.dto.request.WalletChargeRequest;
import bumblebee.xchangepass.domain.wallet.dto.request.WalletGetRequest;
import bumblebee.xchangepass.domain.wallet.dto.request.WalletTransferRequest;
import bumblebee.xchangepass.domain.wallet.dto.response.WalletBalanceResponse;
import bumblebee.xchangepass.domain.wallet.dto.response.WalletTransactionResponse;
import bumblebee.xchangepass.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public List<WalletTransactionResponse> transaction(@RequestBody WalletGetRequest request) {
        return walletService.transaction(request);
    }

    @PostMapping("/charge")
    @ResponseStatus(HttpStatus.CREATED)
    public void charge(@RequestBody WalletChargeRequest request) {
        walletService.charge(request);
    }

    @PutMapping("/transfer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void transfer(@RequestBody WalletTransferRequest request) {
        walletService.transfer(request);
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public List<WalletBalanceResponse> balance(@RequestBody WalletGetRequest request) {
        return walletService.balance(request);
    }


}
