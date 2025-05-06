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
                        type="button">
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
            buttons.forEach(btn => btn.addEventListener("click", () => {
                const xhr = new XMLHttpRequest();
                const lecturerId = btn.id;
                xhr.open("PUT", "/app/api/v1/users/lecturers/" + lecturerId + "/subscriptions/${studentId}");
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.send(JSON.stringify({subscription: lecturerId}));
                btn.innerText = btn.innerText === "Abonnieren" ? "Deabonnieren" : "Abonnieren";
            }));
        });
    </script>
</@base.layout>