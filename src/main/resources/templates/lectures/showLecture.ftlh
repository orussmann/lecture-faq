<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/users/${lecture.userId}/lectures/${lecture.id}" method="post">

        <p>Lecture ID: ${lecture.id}</p>

        <a href="/app/lectures/${lecture.id}/questions">Questions</a><br>

        <label>Lecture title</label><br>
        <input type="text" value="${lecture.title}" placeholder="FDDW" name="title" required><br>

        <label>Lecture description</label>
        <input type="text" value="${lecture.description}" placeholder="A course that focuses on web services"
               name="description" required><br>

        <label>
            Lecture type
            <select name="type" required>
                <option value="ONSITE" <#if lecture.type == "ONSITE">selected</#if>>OnSite</option>
                <option value="ONLINE" <#if lecture.type == "ONLINE">selected</#if>>Online</option>
                <option value="HYBRID" <#if lecture.type == "HYBRID">selected</#if>>Hybrid</option>
            </select>
        </label>

        <label>Lecture link</label>
        <input type="text"
               value="${lecture.link}"
               placeholder="https://test.com"
               name="link"
               pattern="^https?:\/\/(?:www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b(?:[-a-zA-Z0-9()@:%_\+.~#?&\/=]*)$"
               required
        ><br>

        <label>User ID</label>
        <input type="text"
               value="${lecture.userId}"
               placeholder="12345678-abcd-efgh-1234-12aB34Cd56eF"
               name="userId"
               pattern="^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
               required
        ><br>

        <label>Lecture code</label>
        <input type="number" value="${lecture.code?c}" placeholder="123" name="code" min="0" max="32767" required><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

    </form>

    <form action="/app/users/${lecture.userId}/lectures/${lecture.id}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>