package com.example.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailProvider {
  public void send(String to, String subject, String message) {
    // 실제로는 여기에서 smtp 메일 전송
    log.info("[Simulated Email] To: {}", to);
    log.info("[Simulated Email] Subject: {}", subject);
    log.info("[Simulated Email] Message: {}", message);
  }
}
