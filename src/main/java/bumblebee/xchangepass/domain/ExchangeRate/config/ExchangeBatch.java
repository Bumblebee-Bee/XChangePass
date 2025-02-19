package bumblebee.xchangepass.domain.ExchangeRate.config;


import bumblebee.xchangepass.domain.ExchangeRate.dto.response.ExchangeDto;
import bumblebee.xchangepass.domain.ExchangeRate.util.ExchangeUtils;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
public class ExchangeBatch {
    @Autowired
    private ExchangeUtils exchangeUtils;

    @Bean
    public Job exchangeJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("exchangeJob", jobRepository)
                .start(step)
                .build();
    }
    @Bean
    public Step step(JobRepository jobRepository, Tasklet tasklet, PlatformTransactionManager platformTransactionManager){
        return new StepBuilder("step", jobRepository)
                .tasklet(tasklet, platformTransactionManager).build();
    }
    @Bean
    public Tasklet tasklet(){
        return ((contribution, chunkContext) -> {
            List<ExchangeDto> exchangeDTOList = exchangeUtils.getExchangeDataAsDtoList();

            for (ExchangeDto exchangeDto : exchangeDTOList) {
                log.info("통화 : " + exchangeDto.cur_nm());
                log.info("환율 : " + exchangeDto.deal_bas_r());
                // 추가적인 필드가 있다면 출력 또는 활용
            }
            return RepeatStatus.FINISHED;
        });
    }
}