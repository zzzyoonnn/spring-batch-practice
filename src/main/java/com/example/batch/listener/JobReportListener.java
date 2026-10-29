package com.example.batch.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobReportListener {

  private final EmailProvider emailProvider;

  @BeforeJob
  public void beforeJob(JobExecution jobExecution) {
    log.info("Batch job started. Job execution ID: {}", jobExecution.getJobInstance().getId());
  }

  @AfterJob
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.FAILED) {
      emailProvider.send(
              "admin@admin.com",
              "Batch job failed",
              "job id: " + jobExecution.getJobInstance().getId()
      );
    } else {
      log.info("Batch job completed successfully.");
    }
  }
}
