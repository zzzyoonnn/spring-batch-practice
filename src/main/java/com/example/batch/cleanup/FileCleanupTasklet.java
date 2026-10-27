package com.example.batch.cleanup;

import jakarta.annotation.Nullable;
import java.io.File;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;

@Slf4j
@RequiredArgsConstructor
public class FileCleanupTasklet implements Tasklet {

  private final String rootPath;
  private final int retentionDays;

  @Override
  public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
    log.info("File cleanup started");

    LocalDate cutoffDate = LocalDate.now().minusDays(retentionDays);
    File folder = new File(rootPath);
    File[] listOfFiles = folder.listFiles();

    log.info("Root path: {}", rootPath);
    log.info("Cutoff date: {}", cutoffDate);


    if (listOfFiles == null) return RepeatStatus.FINISHED;

    for (File file : listOfFiles) {
      String name = file.getName();

      if (name.endsWith(".log") && name.length() >= 10) {
        // "access_2026-10-28.log" => "2026-10-28"
        String dateStr = name.substring(name.lastIndexOf('_') + 1, name.lastIndexOf('.'));
        LocalDate fileDate = LocalDate.parse(dateStr);

        if (fileDate.isBefore(cutoffDate)) {
          file.delete();
          log.info("Deleted file {}", name);
        }
      }
    }

    log.info("File cleanup finished");
    return RepeatStatus.FINISHED;
  }
}
