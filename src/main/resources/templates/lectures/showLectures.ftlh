<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h2>Lectures</h2>
    <ul>
        <#list lectures as lecture>
            <li>
                <a href="/app/users/${lecture.userId}/lectures/${lecture.id}">
                    Lecture title: ${lecture.title},
                    Lecture description: ${lecture.description},
                    Lecture type: ${lecture.type},
                    Lecture link: ${lecture.link},
                    User ID: ${lecture.userId},
                    Lecture code: ${lecture.code?c}
                </a>
            </li>
        </#list>
    </ul>
    <form action="/app/users/${userId}/lectures" method="post">

        <label>Lecture title</label><br>
        <input type="text" placeholder="FDDW" name="title" required><br>

        <label>Lecture description</label>
        <input type="text" placeholder="A course that focuses on web services" name="description" required><br>

        <label>
            <select name="type" required>
                <option value="ONSITE">OnSite</option>
                <option value="ONLINE">Online</option>
                <option value="HYBRID">Hybrid</option>
            </select>
        </label>

        <label>Lecture link</label>
        <input type="text"
               placeholder="https://test.com"
               name="link"
               pattern="^https?:\/\/(?:www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b(?:[-a-zA-Z0-9()@:%_\+.~#?&\/=]*)$"
               required
        ><br>

        <label>Lecture code</label>
        <input type="number" placeholder="1337" name="code" min="0" max="32767" required><br>

        <button type="submit">Create lecture</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>
