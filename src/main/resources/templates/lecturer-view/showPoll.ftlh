<#import "../base-layout-lecturer.ftlh" as base>
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
            const ws = new WebSocket("/app/poll-completion?pollId=${poll.id}&role=LECTURER")

            const answers = document.querySelectorAll("li");
            const link = document.createElement("a");
            link.href = "/app/users/${userId}/polls/${poll.id}/results";
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

        // If there were multiple polls
        /*const answers = document.querySelectorAll("li")
        document.addEventListener("DOMContentLoaded", () => {
            console.log("DOM content loaded");
            answers.forEach(a => {
                a.addEventListener("click", disableChoiceOptions);
            });
        });

        function disableChoiceOptions(answer) {
            console.log("Answer was chosen: ");
            answers.forEach(a => a.removeEventListener("click", disableChoiceOptions));
            showResultsLink(answer);
        }

        function showResultsLink(answer) {
            const link = document.createElement("a");
            link.href = "/app/users/${userId}/polls/${pollId}/results";
            link.innerHTML = "See results";
            answer.appendChild(link);
        }*/
    </script>
</@base.layout>
