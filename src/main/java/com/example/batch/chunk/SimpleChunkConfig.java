package com.example.batch.chunk;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SimpleChunkConfig {

  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;

  @Bean
  public Job simpleJob() {
    return new JobBuilder("simpleJob", jobRepository)
            .start(simpleStep())
            .build();
  }

  @Bean
  public Step simpleStep() {
    return new StepBuilder("simpleStep", jobRepository)
            .<String, String> chunk(2)
            .reader(menuReader())
            .processor(menuProcessor())
            .writer(menuWriter())
            .build();
  }

  // itemReader
  @Bean
  public ItemReader<String> menuReader() {
    return new ListItemReader<>(Arrays.asList(
            "ice americano",
            "latte",
            "mocha",
            "cappuccino",
            "espresso"
    ));
  }

  // itemProcessor
  @Bean
  public ItemProcessor<String, String> menuProcessor() {
    return item -> item.toUpperCase();
  }

  // itemWriter
  @Bean
  public ItemWriter<String> menuWriter() {
    return items -> {
      log.info("--- Chunk start ---");
      for (String item : items) {
        log.info(item);
      }
      log.info("--- Chunk end ---");
    };
  }
}
