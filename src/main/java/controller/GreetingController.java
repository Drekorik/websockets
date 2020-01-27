package controller;


import dto.Greeting;
import dto.MessageInTheBottle;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(MessageInTheBottle message) {
    return new Greeting("Hello, " + message.getName() + "!");
  }

}
