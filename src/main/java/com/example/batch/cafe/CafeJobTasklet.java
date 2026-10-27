package com.example.batch.cafe;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;

@Slf4j
public class CafeJobTasklet implements Tasklet {

  private int cakeCount = 0;
  private final int ORDER_TARGET = 10;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

    cakeCount++;
    log.info("Cake count: ({}/{})", cakeCount, ORDER_TARGET);

    if (cakeCount >= ORDER_TARGET) {
      log.info("All cakes are ready. Opening the cafe");
      return RepeatStatus.FINISHED;
    }

    return RepeatStatus.CONTINUABLE;
  }
}
