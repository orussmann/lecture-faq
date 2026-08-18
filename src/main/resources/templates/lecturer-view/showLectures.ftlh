<#import "../base-layout-lecturer.ftlh" as base>

<@base.layout>
    <h2>Lectures</h2>
<#-- TODO: <form> entfernen   -->
    <ul>
        <#list allLectures as lecture>
            <li>
                <a href="/app/user/lecturer/lectures/${lecture.id}">
                    Name: ${lecture.title}
                    Typ: ${lecture.type}
                    Beschreibung: ${lecture.description}
                    Link: ${lecture.link}"
                    Nutzer ID:${userId}
                    Vorlesungscode:${lecture.code?c}
                </a>
            </li>
        </#list>
    </ul>
    <form id="lecture-form" action="/app/user/lectures" method="post">

        <label>Lecture title</label><br>
        <input type="text" placeholder="FDDW" name="title" id="title" minlength="1" maxlength="100" required><br>
        <#if errors?? && errors.getFieldError("title")??>
            ${errors.getFieldError("title")["defaultMessage"]}
        </#if>

        <label>Lecture description</label>
        <input type="text" placeholder="A course that focuses on web services" name="description" id="description"
               minlength="1" maxlength="1000" required><br>
        <#if errors?? && errors.getFieldError("description")??>
            ${errors.getFieldError("description")["defaultMessage"]}
        </#if>

        <label>
            <select name="type" id="type" required>
                <option value="ONSITE">OnSite</option>
                <option value="ONLINE">Online</option>
                <option value="HYBRID">Hybrid</option>
            </select>
        </label>
        <#if errors?? && errors.getFieldError("type")??>
            ${errors.getFieldError("type")["defaultMessage"]}
        </#if>

        <label>Lecture link</label>
        <input type="url"
               placeholder="https://test.com"
               name="link"
               id="link"
               minlength="1"
               maxlength="1000"
               required
        ><br>
        <#if errors?? && errors.getFieldError("link")??>
            ${errors.getFieldError("link")["defaultMessage"]}
        </#if>

        <label>Lecture code</label>
        <input type="number" placeholder="123" name="code" id="code" min="1" max="999" required><br>
        <#if errors?? && errors.getFieldError("code")??>
            ${errors.getFieldError("code")["defaultMessage"]}
        </#if>

        <button type="submit" id="submit-button">Create lecture</button>
    </form>
    <br>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const form = document.querySelector("#lecture-form");

            form.addEventListener("submit", (event) => {
                event.preventDefault();

                const ws = new WebSocket(
                    "/app/new-lecture?lecturerId=${userId}"
                );

                ws.onopen = () => {
                    const title = document.querySelector("#title").value;

                    ws.send(title);

                    form.submit()
                };
            });
        });
    </script>
</@base.layout>
