<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h2>Dozenten</h2>
    <ul>
        <#list lecturers as lecturer>
            <li>
                <h3>${lecturer.firstName} ${lecturer.lastName}</h3><br>
                <button
                        id="${lecturer.userId}"
                        class="subscription"
                        type="button"
                        data-subscription="${lecturer.subscribed?c}">
                    <#if lecturer.subscribed>
                        Deabonnieren
                    <#else>
                        Abonnieren
                    </#if>
                </button>
            </li>
        </#list>
    </ul>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const buttons = document.querySelectorAll(".subscription");
            const subscriptions = new Map();
            buttons.forEach(btn => {
                const isSubscribed = btn.getAttribute("data-subscription") === "true";
                const lecturerId = btn.id;
                if (isSubscribed) {
                    const ws = new WebSocket("/app/new-lecture?studentId=${studentId}&lecturerId=" + lecturerId + "&role=STUDENT");
                    ws.id = lecturerId;
                    console.log("Subscribed WS ID: ", ws.id);
                    console.log("Subscribed WS: ", ws);
                    ws.onmessage = function (msg) {
                        window.confirm("A new lecture was created!");
                        console.log("Message: " + msg);
                    };
                    subscriptions.set(lecturerId, ws);
                }
            });

            buttons.forEach(btn => btn.addEventListener("click", () => {
                const xhr = new XMLHttpRequest();
                const lecturerId = btn.id;
                xhr.open("PUT", "/app/api/v1/users/students/${studentId}/subscriptions/" + lecturerId);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(JSON.stringify({subscription: lecturerId}));

                xhr.onload = function () {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        /*const response = JSON.parse(xhr.response);
                        console.log("Server-Antwort:", response);*/
                        console.log("Server response is probably 'OK'");
                    } else {
                        console.error("Fehler bei der Anfrage:", xhr.status, xhr.responseText);
                    }
                };
                xhr.onerror = function () {
                    console.error("Netzwerkfehler oder Server nicht erreichbar");
                };
                console.log("Button pushed with lecturer: " + btn.innerText);
                const isSubscribed = btn.getAttribute("data-subscription") === "true";
                if (!isSubscribed) {
                    const wsConnectionEstablished = subscriptions.has(lecturerId);
                    if (!wsConnectionEstablished) {
                        const ws = new WebSocket("/app/new-lecture?studentId=${studentId}&lecturerId=" + lecturerId + "&role=STUDENT");
                        ws.onmessage = function (msg) {
                            window.confirm("A new lecture was created!");
                            console.log("Message: " + msg);
                        };
                        subscriptions.set(lecturerId, ws);
                    }
                    btn.setAttribute("data-subscription", "true");
                    btn.innerText = "Deabonnieren";
                } else {
                    const ws = subscriptions.get(lecturerId);
                    ws.close();
                    subscriptions.delete(lecturerId);
                    btn.setAttribute("data-subscription", "false");
                    //TODO: Validate state of subscription on the backend -> Overall better solution: SSE
                    btn.innerText = "Abonnieren";
                }
            }));
        });
    </script>
</@base.layout>