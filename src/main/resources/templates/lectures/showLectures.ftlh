<#import "../layout.ftlh" as base>

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
                    Lecture code: ${lecture.code}
                </a>
            </li>
        </#list>
    </ul>
    <form action="/app/users/${userId}/lectures" method="post">

        <label>Lecture title</label><br>
        <input type="text" placeholder="Title" name="title"><br>

        <label>Lecture description</label>
        <input type="text" placeholder="Description" name="description"><br>

        <label>Lecture type</label>
        <input type="text" placeholder="Type" name="type"><br>

        <label>Lecture link</label>
        <input type="text" placeholder="Link" name="link"><br>

        <label>Lecture code</label>
        <input type="text" placeholder="Code" name="code"><br>

        <button type="submit">Create lecture</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>
