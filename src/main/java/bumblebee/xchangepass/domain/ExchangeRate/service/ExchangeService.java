package bumblebee.xchangepass.domain.ExchangeRate.service;

import bumblebee.xchangepass.domain.ExchangeRate.dto.response.ExchangeDto;
import bumblebee.xchangepass.domain.ExchangeRate.entity.Exchange;
import bumblebee.xchangepass.domain.ExchangeRate.repository.ExchangeRepository;
import bumblebee.xchangepass.domain.ExchangeRate.util.ExchangeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeUtils exchangeUtils;
    private final ExchangeRepository exchangeRepository;

    public List<ExchangeDto> getExchangeRates() {
        List<ExchangeDto> exchangeDataAsDtoList = exchangeUtils.getExchangeDataAsDtoList();

        for (ExchangeDto x : exchangeDataAsDtoList) {
            Exchange entity = ExchangeDto.toEntity(x);

            exchangeRepository.findByCurrency(entity.getCurrency())
                    .ifPresentOrElse(
                            existing -> {
                                // rate 값이 변경되었을 때만 업데이트 실행
                                if (!existing.getRate().equals(entity.getRate())) {
                                    existing.setRate(entity.getRate());
                                    existing.setUpdatedAt(entity.getUpdatedAt());
                                    exchangeRepository.save(existing); // 변경된 경우만 업데이트
                                }
                            },
                            () -> exchangeRepository.save(entity) // 없으면 새로 삽입
                    );
        }

        return exchangeDataAsDtoList;
    }
}
