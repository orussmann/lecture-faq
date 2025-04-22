<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/users/${user.userId}" method="post">

        <p>User ID: ${user.userId}</p><br>

        <a href="/app/users/${user.userId}/lectures">Lectures</a><br>
        <a href="/app/users/${user.userId}/polls">Polls</a><br>

        <label>User e-mail</label><br>
        <input type="email" value="${user.email}" placeholder="gamer123@mail.com" name="email" required><br>

        <label>User first name</label><br>
        <input type="text" value="${user.firstName}" placeholder="Max" name="firstName" required><br>

        <label>User last name</label><br>
        <input type="text" value="${user.lastName}" placeholder="Mustermann" name="lastName" required><br>

        <label>
            User role
            <select name="role" required>
                <option value="ADMIN" <#if user.role == "ADMIN">selected</#if>>Admin</option>
                <option value="LECTURER" <#if user.role == "LECTURER">selected</#if>>Lecturer</option>
                <option value="STUDENT" <#if user.role == "STUDENT">selected</#if>>Student</option>
                <option value="MODERATOR" <#if user.role == "MODERATOR">selected</#if>>Moderator</option>
            </select>
        </label>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

    </form>

    <form action="/app/users/${user.userId}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>