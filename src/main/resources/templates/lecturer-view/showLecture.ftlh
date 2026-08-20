<#import "../base-layout-lecturer.ftlh" as base>
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
                message = JSON.parse(msg.data)  // TODO: Fix time formatting -> different format when reloading the page and when receiving new message (also for student view)
                chat.innerHTML += "<p><b>" + message.chatUserName + "</b><br>" + message.text + ", " + message.createdAt + "</p>"
            }
        })
    </script>
<#--Lower section is only for the lecturer/ admin view-->
    <#if notification??>
        <div id="notification">${notification}</div>
    </#if>
    <form action="/app/user/lectures/${lecture.id}" method="post">

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
               value="${userId}"
               placeholder="12345678-abcd-efgh-1234-12aB34Cd56eF"
               name="userId"
               id="userId"
               pattern="^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
               required
        ><br>

        <label>Lecture code</label>
        <input type="number" value="${lecture.code?c}" placeholder="123" name="code" id="code" min="0" max="32767"
               required><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

<#--        <button onclick="updateLectureRestAPI()" type="button">Update via AJAX</button>-->
    </form>

    <form action="/app/user/lectures/${lecture.id}" id="deletion-form" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button id="delete-button">Delete</button>

<#--        <button onclick="deleteLecture()" type="button">Delete via AJAX</button>-->
    </form>
    <div id="deletion-confirmation" hidden>
        <label for="confirmation-input">Please confirm that you want to delete this lecture. Enter ${randomString}</label>
        <input type="text" id="confirmation-input">
        <button id="confirmation-button">Confirm</button>
    </div>
    <script>

        let notification = document.getElementById("notification")
        const deletionForm = document.querySelector("#deletion-form");
        const deleteButton = document.getElementById("delete-button");
        const deletionConfirmation = document.getElementById("deletion-confirmation");
        const conformationButton = document.getElementById("confirmation-button");

        if (notification){
            setTimeout(() => {
                notification.hidden = true;
            }, 3000)
        }

        document.addEventListener("DOMContentLoaded", () => {
            const form = document.querySelector("#deletion-form");
            form.addEventListener("submit", (event) => {
                event.preventDefault();
            });
        });

        const confirmationInput = document.getElementById("confirmation-input")
        confirmationInput.addEventListener("input", () => {
            const input = confirmationInput.value;
            const expected = "${randomString}";
            if (input === expected) {
                conformationButton.addEventListener("click", () => {
                    deletionForm.submit();
                })
            }
        });

        deleteButton.addEventListener("click", () => {
            deletionConfirmation.hidden = false;
        });


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
            xhr.open("PUT", "/app/api/v1/users/${userId}/lectures/${lecture.id}")
            console.log("${userId}")
            console.log("${lecture.id}")
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify(lecture))
        }


        function deleteLecture() {
            const xhr = new XMLHttpRequest()
            xhr.open("DELETE", "/app/api/v1/users/${userId}/lectures/${lecture.id}")
            xhr.send()
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 204) {
                    location.href = "/app/user/lectures"
                }
            }
        }

    </script>
</@base.layout>