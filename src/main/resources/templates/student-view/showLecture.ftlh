<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <div class="card bg-base-100 shadow-xl max-w-4xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">${lecture.title}</h2>
            <div class="card bg-base-200 mb-4">
                <div class="card-body p-4">
                    <h3 class="font-bold text-lg mb-4">Live Chat</h3>
                    <div id="chat" class="bg-base-300 p-4 rounded-lg h-96 overflow-y-auto mb-4 space-y-2">
                        <#list chatMessages as chatMessage>
                            <div class="chat chat-start">
                                <div class="chat-bubble chat-bubble-primary max-w-full">
                                    <div class="chat-header">
                                        <strong>${chatMessage.chatUserName}</strong>
                                        <time class="text-xs opacity-50">${chatMessage.createdAt}</time>
                                    </div>
                                    <p class="break-words whitespace-normal">${chatMessage.text}</p>
                                    <div class="flex items-center gap-2 mt-2 pt-2 border-t border-base-content/20">
                                        <span class="text-xs">Likes: <span id="${chatMessage.id}">${chatMessage.likesCount}</span></span>
                                        <button
                                            type="button"
                                            class="btn btn-xs btn-secondary"
                                            data-liked="${likedQuestionIds?seq_contains(chatMessage.id)?c}"
                                            onclick="toggleLike('${chatMessage.id}', this)">
                                            Like
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                    <div class="flex gap-2">
                        <input type="text" id="text" placeholder="Nachricht..." class="input input-bordered flex-grow" />
                        <button onclick="sendMessage()" class="btn btn-primary">Send</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@base.layout>
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
        document.getElementById("text").value = ""
    }

    document.addEventListener("DOMContentLoaded", () => {
        ws = new WebSocket("/app/chat?roomId=" + "${lecture.id}")
        let chat = document.getElementById("chat")


        ws.onmessage = function (msg) {
            message = JSON.parse(msg.data)
            const chatBubble = document.createElement("div")
            chatBubble.className = "chat chat-start"
            <#noparse>
            chatBubble.innerHTML = `
                <div class="chat-bubble chat-bubble-primary max-w-full">
                    <div class="chat-header">
                        <strong>${message.chatUserName}</strong>
                        <time class="text-xs opacity-50">${message.createdAt}</time>
                    </div>
                    <p class="break-words whitespace-normal">${message.text}</p>
                    <div class="flex items-center gap-2 mt-2 pt-2 border-t border-base-content/20">
                        <span class="text-xs">Likes: <span id="${message.id}">${message.likesCount}</span></span>
                        <button type="button" class="btn btn-xs btn-secondary" data-liked="false" onclick="toggleLike('${message.id}', this)">Like</button>
                    </div>
                </div>
            `
            </#noparse>
            chat.appendChild(chatBubble)
            chat.scrollTop = chat.scrollHeight
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
