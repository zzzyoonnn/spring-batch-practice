package com.example.batch.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ListenerConfig {

  @Bean
  public Step listenerStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder("listenerStep", jobRepository)
            .tasklet((contribution, chunkContext) -> {
              System.out.println("Executing listenerStep...");
              return RepeatStatus.FINISHED;
            }, transactionManager)
            .build();
  }

  @Bean
  public Job listenerJob(JobRepository jobRepository, Step listenerStep, MyAnnotationListener myAnnotationListener) {
    return new JobBuilder("listenerJob", jobRepository)
            .start(listenerStep)
            .listener(myAnnotationListener)
            .build();
  }
}
