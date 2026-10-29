package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyJobListener implements JobExecutionListener {

  @Override
  public void beforeJob(JobExecution jobExecution) {
    log.info("[Job Started] Job Execution ID: {}", jobExecution.getId());
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.FAILED) {
      log.warn("[Job Failed] Job Execution ID: {}. Please contact the administrator.", jobExecution.getId());
    } else {
      log.info("[Job Completed] Job Execution ID: {}", jobExecution.getId());
    }
  }
}
