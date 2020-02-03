package org.websockets.controller;


import org.websockets.dto.MessageInTheBottle;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

  @MessageMapping("/alarm")
  @SendTo("/topic/features")
  public MessageInTheBottle greeting(@Payload MessageInTheBottle message) {
    return message;
  }

}
