<#import "../base-layout-lecturer.ftlh" as base>
<@base.layout>
    <#if notification??>
        <div class="alert alert-info max-w-2xl mx-auto mb-4">
            <span>${notification}</span>
        </div>
    </#if>
    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Profil bearbeiten</h2>
            <form action="/app/user/lecturer/profile" method="post" class="space-y-4">
                
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

                <#--Used before AJAX-->
                <input type="hidden" name="_method" value="PUT"/>
                <div class="form-control mt-6">
                    <button type="submit" class="btn btn-primary w-full">Update</button>
                </div>
                <#--Solution with AJAX-->
<#--        <button onclick="updateCredentials()" type="button">Update</button>-->
            </form>
        </div>
    </div>

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