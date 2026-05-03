<#import "../base-layout-lecturer.ftlh" as base>

<@base.layout>
    <h1>Multiple-Choice Fragen stellen</h1>
    <form action="/app/user/lecturer/poll-form" method="post">
        <input type="text" name="title" placeholder="Titel">
        <#if errors?? && errors.getFieldError("title")??>
            ${errors.getFieldError("title")["defaultMessage"]}
        </#if>
        <input type="text" name="description" placeholder="Beschreibung... (Optional)">
        <#if errors?? && errors.getFieldError("description")??>
            ${errors.getFieldError("description")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[0].text" placeholder="Antwort A">
        <input type="text" name="answers[1].text" placeholder="Antwort B">
        <input type="text" name="answers[2].text" placeholder="Antwort C">
        <input type="text" name="answers[3].text" placeholder="Antwort D">
        <#if errors?? && errors.getFieldError("answers")??>
            ${errors.getFieldError("answers")["defaultMessage"]}
        </#if>
        <button type="submit">Abschicken</button>
    </form>
    <h2>Polls</h2>
    <ul>
        <#list polls as poll>
            <li>
                <a href="/app/user/lecturer/polls/${poll.id}">
                    Poll ID: ${poll.id}, User: ${poll.userId}, Title: ${poll.title},
                    Description: ${poll.description}
                </a>
                <a href="/app/user/lecturer/polls/${poll.id}/results">See results</a>
            </li>
        </#list>
    </ul>
</@base.layout>