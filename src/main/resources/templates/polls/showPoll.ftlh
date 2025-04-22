<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/users/${poll.userId}/polls/${poll.id}" method="post">

        <p>Poll ID: ${poll.id}</p>

        <p>User ID: ${poll.userId}</p>

        <a href="/app/polls/${poll.id}/answers">Answers</a>

        <label>Poll title</label><br>
        <input type="text" value="${poll.title}" name="title" required><br>

        <label>Poll description</label>
        <input type="text" value="${poll.description}" name="description" required><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

    </form>

    <form action="/app/users/${poll.userId}/polls/${poll.id}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>