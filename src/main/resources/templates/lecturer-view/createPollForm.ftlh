<#import "../base-layout-lecturer.ftlh" as base>

<@base.layout>
    <h1>Multiple-Choice Fragen stellen</h1>
    <form action="/app/users/${userId}/polls/poll-form" method="post">
        <input type="text" name="title" placeholder="Titel">
        <#if errors?? && errors.getFieldError("title")??>
            ${errors.getFieldError("title")["defaultMessage"]}
        </#if>
        <input type="text" name="description" placeholder="Beschreibung... (Optional)">
        <#if errors?? && errors.getFieldError("description")??>
            ${errors.getFieldError("description")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[0].text" placeholder="Antwort A">
        <#if errors?? && errors.getFieldError("answers[0].text")??>
            ${errors.getFieldError("answers[0].text")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[1].text" placeholder="Antwort B">
        <#if errors?? && errors.getFieldError("answers[1].text")??>
            ${errors.getFieldError("answers[1].text")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[2].text" placeholder="Antwort C">
        <#if errors?? && errors.getFieldError("answers[2].text")??>
            ${errors.getFieldError("answers[2].text")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[3].text" placeholder="Antwort D">
        <#if errors?? && errors.getFieldError("answers[3].text")??>
            ${errors.getFieldError("answers[3].text")["defaultMessage"]}
        </#if>
        <button type="submit">Abschicken</button>
    </form>
    <h2>Polls</h2>
    <ul>
        <#list polls as poll>
            <li>
                <a href="/app/users/${poll.userId}/polls/${poll.id}">
                    Poll ID: ${poll.id}, User: ${poll.userId}, Title: ${poll.title},
                    Description: ${poll.description}
                </a>
            </li>
        </#list>
    </ul>
</@base.layout>