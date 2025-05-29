<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h2>My Lectures</h2>
    <ul>
        <#list lectures as lecture>
            <li>
                <h3>${lecture.title}</h3>
                <h3>Typ</h3>: ${lecture.type}<br>
                <h3>Beschreibung</h3>: ${lecture.description}
                <button type="button">Beitreten</button>
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
                    const link = document.createElement("a");
                    link.href = "/app/users/${userId}/lectures";
                    document.body.append(link);
                    link.click();
                });
            });
        });
    </script>
</@base.layout>