<#import "../base-layout-lecturer.ftlh" as base>

<@base.layout>
    <h1>Multiple-Choice Fragen stellen</h1>
    <form action="/app/users/${userId}/polls/create-form" method="post">
        <input type="text" name="title" placeholder="Titel">
        <#if errors?? && errors.getFieldError("title")??>
            ${errors.getFieldError("title")["defaultMessage"]}
        </#if>
        <input type="text" name="description" placeholder="Beschreibung... (Optional)">
        <#if errors?? && errors.getFieldError("description")??>
            ${errors.getFieldError("description")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[0]" placeholder="Antwort A">
        <#if errors?? && errors.getFieldError("answers[0]")??>
            ${errors.getFieldError("answers[0]")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[1]" placeholder="Antwort B">
        <#if errors?? && errors.getFieldError("answers[1]")??>
            ${errors.getFieldError("answers[1]")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[2]" placeholder="Antwort C">
        <#if errors?? && errors.getFieldError("answers[2]")??>
            ${errors.getFieldError("answers[2]")["defaultMessage"]}
        </#if>
        <input type="text" name="answers[3]" placeholder="Antwort D">
        <#if errors?? && errors.getFieldError("answers[3]")??>
            ${errors.getFieldError("answers[3]")["defaultMessage"]}
        </#if>
        <button type="submit">Abschicken</button>
    </form>
</@base.layout>