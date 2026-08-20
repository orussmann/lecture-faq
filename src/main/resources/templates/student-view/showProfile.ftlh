<#import "../base-layout-student.ftlh" as base>
<@base.layout>
<#--    <div id="newLecturesContainer"></div>-->
    <form action="/app/user/student/profile" method="post">

        <label>User e-mail</label><br>
        <input type="email" value="${user.email}" placeholder="gamer123@mail.com" name="email" id="email" required><br>

        <label>User first name</label><br>
        <input type="text" value="${user.firstName}" placeholder="Max" name="firstName" id="firstName" required><br>

        <label>User last name</label><br>
        <input type="text" value="${user.lastName}" placeholder="Mustermann" name="lastName" id="lastName" required><br>

        <!--Used before AJAX-->
        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

<#--        <button onclick="updateCredentials()" type="button">Update</button>-->
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const lecturerIds = [<#list lecturerIds as l>"${l}"<#if l_has_next>, </#if></#list>];
                const newLectures = [<#list newLectures as l>"${l}"<#if l_has_next>, </#if></#list>];
                // const newLecturesContainer = document.querySelector("#newLecturesContainer");
                newLectures.forEach(nl => {
                    console.log("New lecture: ", nl);
                    // newLecturesContainer.innerHTML += "<p>" + nl + "</p>";
                    window.alert(nl);
                });
                for (let lecturerId of lecturerIds) {
                    console.log(lecturerId);
                    const ws = new WebSocket("/app/new-lecture?studentId=${user.id}&lecturerId=" + lecturerId + "&role=STUDENT");
                    ws.onmessage = (msg) => {
                        const lectureInfo = JSON.parse(msg.data);
                        const lecturerFullName = lectureInfo.lecturerSurName + " " + lectureInfo.lecturerLastName;
                        const lectureName = lectureInfo.lectureName;
                        window.confirm(lecturerFullName + " hat eine neue Vorlesung  erstellt: \"" + lectureName + "\"");
                    };
                }
            });
        </script>
    </form>

    <#--Update via AJAX-->
<#--    <script>-->
<#--        function updateCredentials() {-->
<#--            const xhr = new XMLHttpRequest()-->
<#--            const user = {}-->
<#--            user.email = document.getElementById("email").value-->
<#--            user.firstName = document.getElementById("firstName").value-->
<#--            user.lastName = document.getElementById("lastName").value-->
<#--            console.log(user)-->
<#--            xhr.open("PUT", "/app/api/v1/users/${user.id}")-->
<#--            console.log("${user.id}")-->
<#--            xhr.setRequestHeader("Content-Type", "application/json")-->
<#--            xhr.send(JSON.stringify(user))-->
<#--        }-->
<#--    </script>-->
</@base.layout>