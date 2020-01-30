package websocket;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertNotNull;

import dto.MessageInTheBottle;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@RunWith(SpringJUnit4ClassRunner.class)
public class WebSocketTest {

  private String URL;

  private static final String SEND_CREATE_ALARM_ENDPOINT = "/app/alarm";
  private static final String SUBSCRIBE_ALARM_ENDPOINT = "/topic/features";

  private CompletableFuture<MessageInTheBottle> completableFuture;

  @Before
  public void setup() {
    int port = 8080;
    completableFuture = new CompletableFuture<>();
    URL = "ws://localhost:" + port + "/features-app";
  }

  @Test
  public void testCreateGameEndpoint()
      throws InterruptedException, ExecutionException, TimeoutException {
    String uuid = UUID.randomUUID().toString();

    WebSocketStompClient stompClient = new WebSocketStompClient(
        new SockJsClient(createTransportClient()));
    stompClient.setMessageConverter(new MappingJackson2MessageConverter());

    StompSession stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
    }).get(1, SECONDS);

    stompSession
        .subscribe(SUBSCRIBE_ALARM_ENDPOINT + uuid, new CreateGameStompFrameHandler());
    stompSession.send(SEND_CREATE_ALARM_ENDPOINT + uuid, null);


    MessageInTheBottle messageInTheBottle = completableFuture.get(10, SECONDS);

    assertNotNull(messageInTheBottle);
  }

  private List<Transport> createTransportClient() {
    List<Transport> transports = new ArrayList<>(1);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    return transports;
  }

  private class CreateGameStompFrameHandler implements StompFrameHandler {
    @Override
    public Type getPayloadType(StompHeaders stompHeaders) {
      return MessageInTheBottle.class;
    }

    @Override
    public void handleFrame(StompHeaders stompHeaders, Object o) {
      completableFuture.complete((MessageInTheBottle) o);
    }
  }
}
