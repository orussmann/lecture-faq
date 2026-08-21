<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <h1>"${lecture.title}"</h1>
    <div id="chat">
        <#list chatMessages as chatMessage>
            <p><b>${chatMessage.chatUserName}</b><br>${chatMessage.text}, ${chatMessage.createdAt}</p>
        </#list>
    </div>
    <input type="text" id="text" placeholder="Nachricht...">
    <br>
    <button onclick="sendMessage()">Send</button>
    <script>
        let ws;
        let message = {};

        function sendMessage() {
            message.lectureId = "${lecture.id}"
            message.userId = "${userId}"
            message.chatUserName = "${userName}"
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
</@base.layout>