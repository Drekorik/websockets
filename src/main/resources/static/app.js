var stompClient = null;

function setConnected(connected) {
  document.getElementById('connect').disabled = connected;
  document.getElementById('disconnect').disabled = !connected;
  document.getElementById('conversationDiv').style.visibility = connected
      ? 'visible' : 'hidden';
  document.getElementById('response').innerHTML = '';
}

function connect() {
  const socket = new WebSocket('ws://localhost:8080/features-app');
  stompClient = Stomp.over(socket);
  stompClient.connect({}, frame => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/features', greeting => {
      showGreeting(JSON.parse(greeting.body).content);
    });
  });
}

function disconnect() {
  stompClient.disconnect();
  setConnected(false);
  console.log("Disconnected");
}

function sendName() {
  var name = document.getElementById('name').value;
  stompClient.send("/app/alarm", {}, JSON.stringify({'name': name}));
}

function showGreeting(message) {
  var response = document.getElementById('response');
  var p = document.createElement('p');
  p.style.wordWrap = 'break-word';
  p.appendChild(document.createTextNode(message));
  response.appendChild(p);
}