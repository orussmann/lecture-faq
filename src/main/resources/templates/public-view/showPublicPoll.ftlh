<!doctype html>
<html lang="en" data-theme="light">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lecture FAQ - ${poll.title}</title>
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
            <a href="/app/public-polls" class="btn btn-ghost">Back to Polls</a>
        </div>
    </nav>
</header>

<main class="flex-grow container mx-auto px-4 py-8">
    <h1 class="text-4xl font-bold text-center mb-8">${poll.title}</h1>
    
    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-4">Wähle eine Antwort</h2>
            <ul class="space-y-3">
                <#list answers as answer>
                    <li id="${answer.id}" class="cursor-pointer">
                        <button class="btn btn-outline w-full text-left justify-start hover:btn-primary h-auto min-h-0 py-2 whitespace-normal text-wrap break-all overflow-hidden">
                            ${answer.text}
                        </button>
                    </li>
                </#list>
            </ul>
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
    document.addEventListener("DOMContentLoaded", () => {
        console.log("DOM content loaded");
        const ws = new WebSocket("/app/poll-completion?pollId=${poll.id}&role=STUDENT")
        const answers = document.querySelectorAll("li");
        const link = document.createElement("a");
        link.href = "/app/public-polls/${poll.id}/results";
        answers.forEach(a => {
            a.addEventListener("click", (event) => {
                console.log(ws.OPEN);
                console.log("clicked");
                ws.send(JSON.stringify({answerId: event.currentTarget.id}))
                document.body.appendChild(link);
                link.click();
                link.remove();
            });
        });

        ws.onerror = function (error) {
            console.log("Error: " + error)
        }

        ws.onclose = function (msg) {
            console.log("Closing connection: " + msg.reason)
        }
    });
</script>
</body>
</html>
