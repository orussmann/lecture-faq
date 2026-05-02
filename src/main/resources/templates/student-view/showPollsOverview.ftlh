<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h2>Polls</h2>
    <ul>
        <#list polls as poll>
            <li>
                <a href="/app/user/student/polls/${poll.id}">
                    Poll ID: ${poll.id}, User: ${poll.userId}, Title: ${poll.title},
                    Description: ${poll.description}
                </a>
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
        });
    </script>
</@base.layout>