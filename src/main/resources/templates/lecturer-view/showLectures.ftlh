<#import "../base-layout-lecturer.ftlh" as base>

<@base.layout>
    <h2>Lectures</h2>
<#-- TODO: <form> entfernen   -->
    <ul>
        <#list allLectures as lecture>
            <li>
                <a href="/app/users/${userId}/lectures/${lecture.id}">
                    <h3>Name:</h3>
                    <p>${lecture.title}</p>
                    <h3>Typ:</h3>
                    <p>${lecture.type}</p><br>
                    <h3>Beschreibung:</h3>
                    <p>${lecture.description}</p><br>
                    <h3>Link:</h3>
                    <p>${lecture.link}</p>
                    <h3>Nutzer ID:</h3>
                    <p>${userId}</p>
                    <h3>Vorlesungscode:</h3>
                    <p>Lecture code</p>: ${lecture.code?c}
                </a>
            </li>
        </#list>
    </ul>
    <form action="/app/users/${userId}/lectures" method="post">

        <label>Lecture title</label><br>
        <input type="text" placeholder="FDDW" name="title" id="title" required><br>

        <label>Lecture description</label>
        <input type="text" placeholder="A course that focuses on web services" name="description" id="description"
               required><br>

        <label>
            <select name="type" id="type" required>
                <option value="ONSITE">OnSite</option>
                <option value="ONLINE">Online</option>
                <option value="HYBRID">Hybrid</option>
            </select>
        </label>

        <label>Lecture link</label>
        <input type="text"
               placeholder="https://test.com"
               name="link"
               id="link"
               pattern="^https?:\/\/(?:www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b(?:[-a-zA-Z0-9()@:%_\+.~#?&\/=]*)$"
               required
        ><br>

        <label>Lecture code</label>
        <input type="number" placeholder="1337" name="code" id="code" min="0" max="32767" required><br>

        <button type="button" id="main-button">Create lecture</button>
    </form>
    <br>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            document.querySelector("#main-button").addEventListener("click", (event) => {
                const ws = new WebSocket("/app/new-lecture?lecturerId=${userId}");
                ws.onopen = () => {
                    const title = document.querySelector("#title").value;
                    console.log("Title of subscribed lecture: ", title);
                    ws.send(title);
                };
                const lecture = {};
                const xhr = new XMLHttpRequest();
                xhr.open("POST", "/app/api/v1/users/${userId}/lectures");
                lecture.title = document.getElementById("title").value;
                lecture.description = document.getElementById("description").value;
                lecture.type = document.getElementById("type").value;
                lecture.link = document.getElementById("link").value;
                lecture.userId = "${userId}";
                lecture.code = document.getElementById("code").value;
                xhr.setRequestHeader("Content-Type", "application/json")
                xhr.send(JSON.stringify(lecture));
            });
        });
    </script>
</@base.layout>
