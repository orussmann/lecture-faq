<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lecture FAQ</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<h1>"${lecture.title}"</h1>
<div id="chat">
    <#list chatMessages as chatMessage>
        <p><b>${chatMessage.chatUserName}</b><br>${chatMessage.text}, ${chatMessage.createdAt}</p>
    </#list>
</div>
<input type="text" id="username" placeholder="Benutzername... (optional)">
<input type="text" id="text" placeholder="Nachricht...">
<br>
<button onclick="sendMessage()">Send</button>
<script>
    let ws;
    let message = {};

    function sendMessage() {
        message.lectureId = "${lecture.id}"
        message.userId = "${userId}"
        let username = document.getElementById("username").value
        message.chatUserName = username.length > 0 ? username : "Anonymer Absender"
        message.text = document.getElementById("text").value
        message.createdAt = new Date().toISOString()
        ws.send(JSON.stringify(message))
    }

    document.addEventListener("DOMContentLoaded", () => {
        ws = new WebSocket("/app/chat?roomId=" + "${lecture.id}")
        let chat = document.getElementById("chat")

        ws.onmessage = function (msg) {
            message = JSON.parse(msg.data)
            chat.innerHTML += "<p><b>" + message.chatUserName + "</b><br>" + message.text + ", " + message.createdAt + "</p>"
        }
    })
</script>
<footer>
    <a href="#">Imprint</a>
    <a href="#">About us</a>
    <a href="#">Support</a>
</footer>
</body>
</html>
