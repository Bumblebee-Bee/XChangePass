package bumblebee.xchangepass.domain.ExchangeRate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeBatchService {

    private final JobLauncher jobLauncher;
    private final Job exchangeJob;

    public void runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()) // 실행 시간마다 새로운 파라미터 추가
                    .toJobParameters();

            jobLauncher.run(exchangeJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}