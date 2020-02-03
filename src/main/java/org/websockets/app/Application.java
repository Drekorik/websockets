package org.websockets.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication(scanBasePackages = "org.websockets.*")
@EnableWebSocketMessageBroker
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
