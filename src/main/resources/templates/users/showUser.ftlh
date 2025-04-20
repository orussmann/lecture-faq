<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/users/${user.userId}" method="post">

        <p>User ID: ${user.userId}></p>

        <a href="/app/users/${user.userId}/lectures">Lectures</a>
        <a href="/app/users/${user.userId}/polls">Polls</a>

        <label>User e-mail</label><br>
        <input type="text" placeholder="User e-mail" name="email"><br>

        <label>User first name</label><br>
        <input type="text" placeholder="User first name" name="firstName"><br>

        <label>User last name</label><br>
        <input type="text" placeholder="User last name" name="lastName"><br>

        <label>Question role</label><br>
        <input type="text" placeholder="User role" name="role"><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

    </form>

    <form action="/app/users/${user.userId}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>