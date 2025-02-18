package bumblebee.xchangepass.domain.ExchangeRate.service;

import bumblebee.xchangepass.domain.ExchangeRate.dto.response.ExchangeDto;
import bumblebee.xchangepass.domain.ExchangeRate.util.ExchangeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeUtils exchangeUtils;

    public List<ExchangeDto> getExchangeRates() {
        return exchangeUtils.getExchangeDataAsDtoList();
    }
}
