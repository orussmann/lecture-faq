<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h2>Dozenten</h2>
    <ul>
        <#list lecturers as l>
            <li>
                <h3>${l.lecturerFirstName} ${l.lecturerLastName}</h3><br>
                <button
                        id="${l.lecturerId}"
                        class="subscription"
                        type="button"
                        data-subscription="${l.subscribed?c}">
                    <#if l.subscribed>
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
            const lecturerIds = [<#list lecturerIds as l>"${l}"<#if l_has_next>, </#if></#list>];
            for (let lecturerId of lecturerIds) {
                console.log(lecturerId);
                const ws = new WebSocket("/app/new-lecture?studentId=${studentId}&lecturerId=" + lecturerId + "&role=STUDENT");
                ws.onmessage = (msg) => {
                    const lectureInfo = JSON.parse(msg.data);
                    const lecturerFullName = lectureInfo.lecturerSurName + " " + lectureInfo.lecturerLastName;
                    const lectureName = lectureInfo.lectureName;
                    window.confirm(lecturerFullName + " hat eine neue Vorlesung  erstellt: \"" + lectureName + "\"");
                };
            }

            const buttons = document.querySelectorAll(".subscription");

            buttons.forEach(btn => btn.addEventListener("click", () => {
                const xhr = new XMLHttpRequest();
                const lecturerId = btn.id;
                xhr.open("PUT", "/app/api/v1/user/students/${studentId}/subscriptions/" + lecturerId);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(JSON.stringify({subscription: lecturerId}));
                // TODO: Now a page reload is needed to load updated subscriptions state -> SSE would be a better solution,
                //  but for practicing websockets this solution is fine
                xhr.onload = function () {
                    if (xhr.status >= 200 && xhr.status < 300) {
                        /*const response = JSON.parse(xhr.response);
                        console.log("Server-Antwort:", response);*/
                        console.log("Server response is probably 'OK'");
                        btn.innerText = btn.innerText === "Deabonnieren" ? "Abonnieren" : "Deabonnieren";
                    } else {
                        console.error("Fehler bei der Anfrage:", xhr.status, xhr.responseText);
                    }
                };
                xhr.onerror = function () {
                    console.error("Netzwerkfehler oder Server nicht erreichbar");
                };
            }));
        });
    </script>
</@base.layout>