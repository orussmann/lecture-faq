<#import "../base-layout-lecturer.ftlh" as base>
<@base.layout>
    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">${poll.title}</h2>
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
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            console.log("DOM content loaded");
            const ws = new WebSocket("/app/poll-completion?pollId=${poll.id}&role=LECTURER")

            const answers = document.querySelectorAll("li");
            const link = document.createElement("a");
            link.href = "/app/user/lecturer/polls/${poll.id}/results";
            answers.forEach(a => {
                a.addEventListener("click", (event) => {
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
            link.href = "/app/users/${poll.userId}/polls/${poll.id}/results";
            link.innerHTML = "See results";
            answer.appendChild(link);
        }*/
    </script>
</@base.layout>
