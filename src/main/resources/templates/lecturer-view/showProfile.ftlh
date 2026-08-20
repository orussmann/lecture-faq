<#import "../base-layout-lecturer.ftlh" as base>
<@base.layout>
    <form action="/app/user/lecturer/profile" method="post">

        <label>User e-mail</label><br>
        <input type="email" value="${user.email}" placeholder="gamer123@mail.com" name="email" id="email" minlength="1" maxlength="100" required><br>
        <#if errors?? && errors.getFieldError("email")??>
            ${errors.getFieldError("email")["defaultMessage"]}
        </#if>

        <label>User first name</label><br>
        <input type="text" value="${user.firstName}" placeholder="Max" name="firstName" id="firstName" minlength="1" maxlength="100" required><br>
        <#if errors?? && errors.getFieldError("firstName")??>
            ${errors.getFieldError("firstName")["defaultMessage"]}
        </#if>

        <label>User last name</label><br>
        <input type="text" value="${user.lastName}" placeholder="Mustermann" name="lastName" id="lastName" minlength="1" maxlength="100" required><br>
        <#if errors?? && errors.getFieldError("lastName")??>
            ${errors.getFieldError("lastName")["defaultMessage"]}
        </#if>

        <#--Used before AJAX-->
        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>
        <#--Solution with AJAX-->
<#--        <button onclick="updateCredentials()" type="button">Update</button>-->
    </form>

<#--    <script>-->
<#--        function updateCredentials() {-->
<#--            const xhr = new XMLHttpRequest()-->
<#--            const user = {}-->
<#--            user.email = document.getElementById("email").value-->
<#--            user.firstName = document.getElementById("firstName").value-->
<#--            user.lastName = document.getElementById("lastName").value-->
<#--            console.log(user)-->
<#--            xhr.open("PUT", "/app/api/v1/user/${user.id}")-->
<#--            console.log("${user.id}")-->
<#--            xhr.setRequestHeader("Content-Type", "application/json")-->
<#--            xhr.send(JSON.stringify(user))-->
<#--        }-->
<#--    </script>-->
</@base.layout>