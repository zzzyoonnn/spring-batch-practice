package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyAnnotationListener {

  @BeforeJob
  public void announceStart(JobExecution jobExecution) {
    log.info("[Job Started] Job Instance ID: {}", jobExecution.getJobInstanceId());
  }

  @AfterJob
  public void announceEnd(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.FAILED) {
      log.warn("[Job Failed] Job Instance ID: {}. Please contact the administrator.", jobExecution.getJobInstanceId());
    } else {
      log.info("[Job Completed] Job Instance ID: {}", jobExecution.getJobInstanceId());
    }
  }
}
