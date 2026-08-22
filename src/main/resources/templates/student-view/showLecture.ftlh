<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <h1>"${lecture.title}"</h1>
    <div id="chat">
        <#list chatMessages as chatMessage>

            <div>
                <p>
                    <b>${chatMessage.chatUserName}</b>
                    <br>
                    ${chatMessage.text}
                </p>

                <div>
                    Likes:
                    <span id="${chatMessage.id}">
                ${chatMessage.likesCount}
            </span>
                </div>

                <button
                        type="button"
                        data-liked="${likedQuestionIds?seq_contains(chatMessage.id)?c}"
                        onclick="toggleLike('${chatMessage.id}', this)">
                    Like
                </button>
            </div>

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
                <#noparse>
                chat.innerHTML += `
                    <div>
                        <p>
                            <b>${message.chatUserName}</b><br>
                            ${message.text}, ${message.createdAt}
                        </p>

                        <div>
                            Likes: <span id="${message.id}">${message.likesCount}</span>
                        </div>

                        <button
                            type="button"
                            data-liked="false"
                            onclick="toggleLike('${message.id}', this)">
                            Like
                        </button>
                    </div>
                `;
                </#noparse>
            }
        })

        function sendLikeState(questionId, liked, likesCounter) {
            const xhr = new XMLHttpRequest();
            xhr.responseType = "json";
            xhr.open("PATCH", "/app/api/v1/lectures/${lecture.id}/questions/" + questionId + "/likes");
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify({liked: liked}));
            xhr.onload = function () {
                if (xhr.status >= 200 && xhr.status < 300) {
                    /*const response = JSON.parse(xhr.response);
                    console.log("Server-Antwort:", response);*/
                    console.log("Server response is probably 'OK'");

                    console.log("Response:", xhr.response);
                    console.log("Likes:", xhr.response.likes);
                    likesCounter.textContent = liked ? Number(likesCounter.textContent) + 1 : Number(likesCounter.textContent) - 1
                    // btn.innerText = btn.innerText === "Deabonnieren" ? "Abonnieren" : "Deabonnieren";
                } else {
                    console.error("Fehler bei der Anfrage:", xhr.status, xhr.responseText);
                }
            };
            xhr.onerror = function () {
                console.error("Netzwerkfehler oder Server nicht erreichbar");
            };
        }

        const likeTimeouts = {};

        function toggleLike(questionId, button) {
            const likesCounter = document.getElementById(questionId);

            const currentlyLiked = button.dataset.liked === "true";
            const liked = !currentlyLiked;

            button.dataset.liked = String(liked);

            clearTimeout(likeTimeouts[questionId]);

            likeTimeouts[questionId] = setTimeout(() => {
                sendLikeState(questionId, liked, likesCounter);
            }, 150);
        }
    </script>
</@base.layout>