package bumblebee.xchangepass.domain.ExchangeRate.controller;

import bumblebee.xchangepass.domain.ExchangeRate.dto.response.ExchangeDto;
import bumblebee.xchangepass.domain.ExchangeRate.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping("/rates")
    public List<ExchangeDto> getExchangeRates() {
        return exchangeService.getExchangeRates();
    }
}
