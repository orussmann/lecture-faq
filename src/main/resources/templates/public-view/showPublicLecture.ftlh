<!doctype html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lecture FAQ - ${lecture.title}</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5" rel="stylesheet" type="text/css" />
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@5/themes.css" rel="stylesheet" type="text/css" />
</head>
<body class="min-h-screen flex flex-col bg-base-200">
<header>
    <nav class="navbar bg-base-100 shadow-lg">
        <div class="navbar-start">
            <div class="dropdown">
                <div tabindex="0" role="button" class="btn btn-ghost lg:hidden">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h8m-8 6h16" />
                    </svg>
                </div>
                <ul tabindex="0" class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">
                    <li><a href="/app/public-polls">Multiple-Choice-Fragen</a></li>
                    <li><a href="/app/public-lectures">Lectures</a></li>
                </ul>
            </div>
            <a href="/app/" class="btn btn-ghost text-xl">Lecture FAQ</a>
        </div>
        <div class="navbar-center hidden lg:flex">
            <ul class="menu menu-horizontal px-1">
                <li><a href="/app/public-polls">Multiple-Choice-Fragen</a></li>
                <li><a href="/app/public-lectures">Lectures</a></li>
            </ul>
        </div>
        <div class="navbar-end">
            <a href="/app/public-lectures" class="btn btn-ghost">Back to Lectures</a>
        </div>
    </nav>
</header>

<main class="flex-grow container mx-auto px-4 py-8">
    <h1 class="text-4xl font-bold text-center mb-8">${lecture.title}</h1>
    
    <div class="card bg-base-100 shadow-xl mb-8">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-4">Live Chat</h2>
            <div id="chat" class="bg-base-200 p-4 rounded-lg h-96 overflow-y-auto mb-4 space-y-2">
                <#list chatMessages as chatMessage>
                    <div class="chat chat-start">
                        <div class="chat-bubble chat-bubble-primary">
                            <div class="chat-header">
                                <strong>${chatMessage.chatUserName}</strong>
                                <time class="text-xs opacity-50">${chatMessage.createdAt}</time>
                            </div>
                            <p>${chatMessage.text}</p>
                        </div>
                    </div>
                </#list>
            </div>
            
            <div class="flex gap-2">
                <input type="text" id="username" placeholder="Benutzername... (optional)" class="input input-bordered w-1/3" />
                <input type="text" id="text" placeholder="Nachricht..." class="input input-bordered flex-grow" />
                <button onclick="sendMessage()" class="btn btn-primary">Send</button>
            </div>
        </div>
    </div>
</main>

<footer class="footer footer-center p-10 bg-base-300 text-base-content rounded">
    <nav class="grid grid-flow-col gap-4">
        <a href="#" class="link link-hover">Imprint</a>
        <a href="#" class="link link-hover">About us</a>
        <a href="#" class="link link-hover">Support</a>
    </nav>
    <aside>
        <p>Lecture FAQ © 2026 - All rights reserved</p>
    </aside>
</footer>

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
                <div class="chat-bubble chat-bubble-primary">
                    <div class="chat-header">
                        <strong>${message.chatUserName}</strong>
                        <time class="text-xs opacity-50">${message.createdAt}</time>
                    </div>
                    <p>${message.text}</p>
                </div>
            `
            </#noparse>
            chat.appendChild(chatBubble)
            chat.scrollTop = chat.scrollHeight
        }
    })
</script>
</body>
</html>
