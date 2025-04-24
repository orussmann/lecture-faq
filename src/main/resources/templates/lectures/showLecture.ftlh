<#import "../layout.ftlh" as base>
<@base.layout>
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
            message.userId = "${lecture.userId}"
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
<#--Lower section is only for the lecturer/ admin view-->
    <form action="/app/users/${lecture.userId}/lectures/${lecture.id}" method="post">

        <p>Lecture ID: ${lecture.id}</p>

        <a href="/app/lectures/${lecture.id}/questions">Questions</a><br>

        <label>Lecture title</label><br>
        <input type="text" value="${lecture.title}" placeholder="FDDW" name="title" id="title" required><br>

        <label>Lecture description</label>
        <input type="text" value="${lecture.description}" placeholder="A course that focuses on web services"
               name="description" id="description" required><br>

        <label>
            Lecture type
            <select name="type" id="type" required>
                <option value="ONSITE" <#if lecture.type == "ONSITE">selected</#if>>OnSite</option>
                <option value="ONLINE" <#if lecture.type == "ONLINE">selected</#if>>Online</option>
                <option value="HYBRID" <#if lecture.type == "HYBRID">selected</#if>>Hybrid</option>
            </select>
        </label>

        <label>Lecture link</label>
        <input type="text"
               value="${lecture.link}"
               placeholder="https://test.com"
               name="link"
               id="link"
               pattern="^https?:\/\/(?:www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b(?:[-a-zA-Z0-9()@:%_\+.~#?&\/=]*)$"
               required
        ><br>

        <label>User ID</label>
        <input type="text"
               value="${lecture.userId}"
               placeholder="12345678-abcd-efgh-1234-12aB34Cd56eF"
               name="userId"
               id="userId"
               pattern="^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
               required
        ><br>

        <label>Lecture code</label>
        <input type="number" value="${lecture.code?c}" placeholder="123" name="code" id="code" min="0" max="32767"
               required><br>

        <#--<input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>-->

        <button onclick="updateLectureRestAPI()" type="button">Update via AJAX</button>
    </form>

    <form action="/app/users/${lecture.userId}/lectures/${lecture.id}" method="post">

        <#--<input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>-->

        <button onclick="deleteLecture()" type="button">Delete via AJAX</button>
    </form>
    <script>
        //TODO: RegEx for pattern should be updated -> it's not a valid regex
        function updateLectureRestAPI() {
            const xhr = new XMLHttpRequest()
            const lecture = {}
            lecture.title = document.getElementById("title").value
            lecture.description = document.getElementById("description").value
            lecture.type = document.getElementById("type").value
            lecture.link = document.getElementById("link").value
            lecture.userId = document.getElementById("userId").value
            lecture.code = document.getElementById("code").value
            console.log(lecture)
            xhr.open("PUT", "/app/api/v1/users/${lecture.userId}/lectures/${lecture.id}")
            console.log("${lecture.userId}")
            console.log("${lecture.id}")
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify(lecture))
        }


        function deleteLecture() {
            const xhr = new XMLHttpRequest()
            xhr.open("DELETE", "/app/api/v1/users/${lecture.userId}/lectures/${lecture.id}")
            xhr.send()
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 204) {
                    location.href = "/app/users/${lecture.userId}/lectures"
                }
            }
        }

    </script>
</@base.layout>