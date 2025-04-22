<#import "../layout.ftlh" as base>

<@base.layout>
    <h2>Users</h2>
    <ul>
        <#list users as user>
            <li>
                <a href="/app/users/${user.userId}">
                    User ID: ${user.userId},
                    E-mail: ${user.email},
                    First name: ${user.firstName},
                    Last name: ${user.lastName},
                    Role: ${user.role}
                </a>
            </li>
        </#list>
    </ul>
    <form action="/app/users" method="post">

        <label>User e-mail</label><br>
        <input type="email"  placeholder="gamer123@mail.com" name="email" required><br>

        <label>User first name</label><br>
        <input type="text" placeholder="Max" name="firstName" required><br>

        <label>User last name</label><br>
        <input type="text" placeholder="Mustermann" name="lastName" required><br>

        <label>
            <select name="role" required>
                <option value="ADMIN">Admin</option>
                <option value="LECTURER">Lecturer</option>
                <option value="STUDENT">Student</option>
                <option value="MODERATOR">Moderator</option>
            </select>
        </label>
        <button type="submit">Create user</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>
