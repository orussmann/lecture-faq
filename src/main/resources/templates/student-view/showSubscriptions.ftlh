<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Dozenten</h2>
            <ul class="space-y-3">
                <#list lecturers as l>
                    <li class="card bg-base-200">
                        <div class="card-body p-4">
                            <h3 class="font-bold text-lg">${l.lecturerFirstName} ${l.lecturerLastName}</h3>
                            <div class="card-actions justify-end mt-2">
                                <button
                                    id="${l.lecturerId}"
                                    class="subscription btn btn-sm"
                                    type="button"
                                    data-subscription="${l.subscribed?c}">
                                    <#if l.subscribed>
                                        Deabonnieren
                                    <#else>
                                        Abonnieren
                                    </#if>
                                </button>
                            </div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
    <script>
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
    </script>
</@base.layout>