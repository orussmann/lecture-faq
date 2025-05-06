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
            document.querySelectorAll("button").forEach(btn => {
                btn.addEventListener("click", () => {
                    const link = document.createElement("a");
                    link.href = "/app/users/" + ${userId}+ "/lectures";
                    document.body.append(link);
                    link.click();
                });
            });
        });
    </script>
</@base.layout>