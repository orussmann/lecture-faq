<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h2>My Lectures</h2>
    <ul>
        <#list allLectures as lecture>
            <li>
                <a href="/app/user/lectures/${lecture.id}">
                    <h3>Name:</h3>
                    <p>${lecture.title}</p>
                    <h3>Typ:</h3>
                    <p>${lecture.type}</p><br>
                    <h3>Beschreibung:</h3>
                    <p>${lecture.description}</p><br>
                    <h3>Link:</h3>
                    <p>${lecture.link}</p>
                    <h3>Vorlesungscode:</h3>
                    <p>Lecture code</p>: ${lecture.code?c}
                </a>
                <button type="button" data-lecture-id="${lecture.id}">Beitreten</button>
            </li>
        </#list>
    </ul>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const lecturerIds = [<#list lecturerIds as l>"${l}"<#if l_has_next>, </#if></#list>];
            for (let lecturerId of lecturerIds) {
                console.log(lecturerId);
                const ws = new WebSocket("/app/new-lecture?studentId=${userId}&lecturerId=" + lecturerId + "&role=STUDENT");
                ws.onmessage = (msg) => {
                    const lectureInfo = JSON.parse(msg.data);
                    const lecturerFullName = lectureInfo.lecturerSurName + " " + lectureInfo.lecturerLastName;
                    const lectureName = lectureInfo.lectureName;
                    window.confirm(lecturerFullName + " hat eine neue Vorlesung  erstellt: \"" + lectureName + "\"");
                };
            }
            document.querySelectorAll("button").forEach(btn => {
                btn.addEventListener("click", () => {
                    const lectureId = btn.id;
                    const xhr = new XMLHttpRequest();
                    xhr.open("PUT", "/app/api/v1/users/${userId}/lectures/" + lectureId);
                    xhr.setRequestHeader("Content-Type", "application/json");
                    xhr.send(JSON.stringify(""));   // TODO
                });
            });
        });
    </script>
</@base.layout>