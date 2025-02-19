package bumblebee.xchangepass.domain.ExchangeRate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
@RequiredArgsConstructor
public class ExchangeBatchScheduler {

    private final ExchangeBatchService exchangeBatchService;

    @Scheduled(cron = "0 0 11 * * ?") // 매일 오전 11시 실행
    public void scheduleBatchJob() {
        exchangeBatchService.runBatchJob();
    }
}
