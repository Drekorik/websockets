import dto.MessageInTheBottle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  @EventListener
  private void handleSessionConnected(SessionConnectEvent event) {
    MessageInTheBottle messageInTheBottle = new MessageInTheBottle();
    messageInTheBottle.setName("Kate");
    messageInTheBottle.setText("Hello");
    messagingTemplate.convertAndSend("/topic/features", messageInTheBottle);
  }

  @EventListener
  private void handleSessionDisconnect(SessionDisconnectEvent event) {

  }
}
