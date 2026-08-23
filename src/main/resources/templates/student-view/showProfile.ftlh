<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <#if notification??>
        <div class="alert alert-info max-w-2xl mx-auto mb-4">
            <span>${notification}</span>
        </div>
    </#if>
    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Profil bearbeiten</h2>
            <form action="/app/user/student/profile" method="post" class="space-y-4">

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">E-Mail</span>
                    </label>
                    <input type="email" value="${user.email}" placeholder="gamer123@mail.com" name="email" id="email" minlength="1" maxlength="100" required class="input input-bordered w-full" />
                    <#if errors?? && errors.getFieldError("email")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("email")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Vorname</span>
                    </label>
                    <input type="text" value="${user.firstName}" placeholder="Max" name="firstName" id="firstName" minlength="1" maxlength="100" required class="input input-bordered w-full" />
                    <#if errors?? && errors.getFieldError("firstName")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("firstName")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Nachname</span>
                    </label>
                    <input type="text" value="${user.lastName}" placeholder="Mustermann" name="lastName" id="lastName" minlength="1" maxlength="100" required class="input input-bordered w-full" />
                    <#if errors?? && errors.getFieldError("lastName")??>
                        <label class="label">
                            <span class="label-text-alt text-error">${errors.getFieldError("lastName")["defaultMessage"]}</span>
                        </label>
                    </#if>
                </div>

                <input type="hidden" name="_method" value="PUT"/>
                <div class="form-control mt-6">
                    <button type="submit" class="btn btn-primary w-full">Update</button>
                </div>
            </form>
        </div>
    </div>

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
                    const ws = new WebSocket("/app/new-lecture?studentId=${userId}&lecturerId=" + lecturerId + "&role=STUDENT");
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