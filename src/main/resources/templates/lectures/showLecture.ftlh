<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/users/${lecture.userId}/lectures/${lecture.id}" method="post">

        <p>Lecture ID: ${lecture.id}</p>

        <label>Lecture title</label><br>
        <input type="text" value="${lecture.title}" name="title"><br>

        <label>Lecture description</label>
        <input type="text" value="${lecture.description}" name="description"><br>

        <label>Lecture type</label>
        <input type="text" value="${lecture.type}" name="type"><br>

        <label>Lecture link</label>
        <input type="text" value="${lecture.link}" name="link"><br>

        <label>User ID</label>
        <input type="text" value="${lecture.userId}" name="userId"><br>

        <label>Lecture code</label>
        <input type="text" value="${lecture.code}" name="code"><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

    </form>

    <form action="/app/users/${lecture.userId}/lectures/${lecture.id}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>