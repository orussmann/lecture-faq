<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/users/${user.userId}" method="post">

        <p>User ID: ${user.userId}</p><br>

        <a href="/app/users/${user.userId}/lectures">Lectures</a><br>
        <a href="/app/users/${user.userId}/polls">Polls</a><br>

        <label>User e-mail</label><br>
        <input type="email" value="${user.email}" placeholder="gamer123@mail.com" name="email" id="email" required><br>

        <label>User first name</label><br>
        <input type="text" value="${user.firstName}" placeholder="Max" name="firstName" id="firstName" required><br>

        <label>User last name</label><br>
        <input type="text" value="${user.lastName}" placeholder="Mustermann" name="lastName" id="lastName" required><br>

        <label>
            User role
            <select name="role" id="role" required>
                <option value="ADMIN" <#if user.role == "ADMIN">selected</#if>>Admin</option>
                <option value="LECTURER" <#if user.role == "LECTURER">selected</#if>>Lecturer</option>
                <option value="STUDENT" <#if user.role == "STUDENT">selected</#if>>Student</option>
                <option value="MODERATOR" <#if user.role == "MODERATOR">selected</#if>>Moderator</option>
            </select>
        </label>
        <!--Used before AJAX-->
        <#--<input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>
-->
        <button onclick="updateUserRestAPI()" type="button">Update via AJAX! Let's Go!!!</button>
    </form>

    <form action="/app/users/${user.userId}" method="post">

        <#--<input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>
-->
        <button onclick="deleteUser()" type="button">Delete via AJAX</button>
    </form>

    <script>
        function updateUserRestAPI() {
            const xhr = new XMLHttpRequest()
            const user = {}
            user.email = document.getElementById("email").value
            user.firstName = document.getElementById("firstName").value
            user.lastName = document.getElementById("lastName").value
            user.role = document.getElementById("role").value
            console.log(user)
            xhr.open("PUT", "/app/api/v1/users/${user.userId}")
            console.log("${user.userId}")
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify(user))
        }


        function deleteUser() {
            console.log("Delete!")
            const xhr = new XMLHttpRequest()
            xhr.open("DELETE", "/app/api/v1/users/${user.userId}")
            xhr.send()
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 204) {
                    console.log("User successfully deleted!")
                    location.href = "/app/users"
                }
            }
        }

    </script>
</@base.layout>