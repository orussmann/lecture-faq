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
<h1>${poll.title}</h1>
<ul>
    <#list answers as answer>
        <li id="${answer.id}">
            ${answer.text}
        </li>
    </#list>
</ul>
<a href="/app/">Back</a>
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
                ws.send(JSON.stringify({answerId: event.target.id}))
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
<footer>
    <a href="#">Imprint</a>
    <a href="#">About us</a>
    <a href="#">Support</a>
</footer>
</body>
</html>
