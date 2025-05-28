<#import "../base-layout-lecturer.ftlh" as base>
<@base.layout>
    <form action="/app/users/${user.userId}" method="post">

        <label>User e-mail</label><br>
        <input type="email" value="${user.email}" placeholder="gamer123@mail.com" name="email" id="email" required><br>

        <label>User first name</label><br>
        <input type="text" value="${user.firstName}" placeholder="Max" name="firstName" id="firstName" required><br>

        <label>User last name</label><br>
        <input type="text" value="${user.lastName}" placeholder="Mustermann" name="lastName" id="lastName" required><br>

        <!--Used before AJAX-->
        <#--<input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>
-->
        <button onclick="updateCredentials()" type="button">Update</button>
    </form>

    <script>
        function updateCredentials() {
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
    </script>
</@base.layout>