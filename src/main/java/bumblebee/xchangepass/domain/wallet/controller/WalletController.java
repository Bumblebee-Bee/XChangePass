package bumblebee.xchangepass.domain.wallet.controller;

import bumblebee.xchangepass.domain.wallet.dto.request.WalletChargeRequest;
import bumblebee.xchangepass.domain.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    @PostMapping("/charge")
    @ResponseStatus(HttpStatus.CREATED)
    public void charge(@RequestBody WalletChargeRequest request){
        walletService.charge(request);
    }

}
