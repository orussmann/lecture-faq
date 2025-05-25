<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            console.log("DOM content loaded");
            const ws = new WebSocket("/app/new-lecture?studentId=${studentId}");
            ws.onmessage = function (msg) {
                window.confirm("A new lecture was created!");
                console.log("Message: " + msg);
            }
        });
    </script>
</@base.layout>
