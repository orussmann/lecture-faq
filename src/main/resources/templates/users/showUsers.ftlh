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
        <input type="text" placeholder="User e-mail" name="email"><br>

        <label>User first name</label><br>
        <input type="text" placeholder="User first name" name="firstName"><br>

        <label>User last name</label><br>
        <input type="text" placeholder="User last name" name="lastName"><br>

        <label>Question role</label><br>
        <input type="text" placeholder="User role" name="role"><br>

        <button type="submit">Create user</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>
