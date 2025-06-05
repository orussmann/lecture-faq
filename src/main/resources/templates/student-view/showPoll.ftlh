<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <h1>${poll.title}</h1>
    <ul>
        <#list answers as answer>
            <li id="${answer.id}">
                ${answer.text}
            </li>
        </#list>
    </ul>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            console.log("DOM content loaded");
            const ws = new WebSocket("/app/poll-completion?pollId=${poll.id}&role=STUDENT")

            const answers = document.querySelectorAll("li");
            const link = document.createElement("a");
            link.href = "/app/user/polls/${poll.id}/results";
            answers.forEach(a => {
                a.addEventListener("click", (event) => {
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
</@base.layout>
