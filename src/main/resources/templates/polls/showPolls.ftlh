<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h1>Polls</h1>
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
    <p>Please provide at least <b>two</b> answers</p>
    <form action="/app/user/polls" method="post">

        <label>Poll title</label><br>
        <input type="text" placeholder="What is a web server?" name="title" required><br>
        <#if errors?? && errors.getFieldError("title")??>
            ${errors.getFieldError("title")["defaultMessage"]}
        </#if>

        <label>Poll description</label>
        <input type="text" placeholder="Describe the ins and outs of a web server..." name="description" required><br>
        <#if errors?? && errors.getFieldError("description")??>
            ${errors.getFieldError("description")["defaultMessage"]}
        </#if>

        <label>Answer 1</label>
        <input type="text" placeholder="It serves data to a web client.." name="answers[0].text" required><br>
        <#if errors?? && errors.getFieldError("answers[0].text")??>
            ${errors.getFieldError("answers[0].text")["defaultMessage"]}
        </#if>

        <label>Answer 2</label>
        <input type="text" placeholder="Its communication is based on HTTP..." name="answers[1].text" required><br>
        <#if errors?? && errors.getFieldError("answers[1].text")??>
            ${errors.getFieldError("answers[1].text")["defaultMessage"]}
        </#if>

        <label>Answer 3</label>
        <input type="text" placeholder="Answer 3" name="answers[2].text"><br>
        <#if errors?? && errors.getFieldError("answers[2].text")??>
            ${errors.getFieldError("answers[2].text")["defaultMessage"]}
        </#if>

        <label>Answer 4</label>
        <input type="text" placeholder="Answer 4" name="answers[3].text"><br>
        <#if errors?? && errors.getFieldError("answers[3].text")??>
            ${errors.getFieldError("answers[3].text")["defaultMessage"]}
        </#if>

        <label>Answer 5</label>
        <input type="text" placeholder="Answer 5" name="answers[4].text"><br>
        <#if errors?? && errors.getFieldError("answers[4].text")??>
            ${errors.getFieldError("answers[4].text")["defaultMessage"]}
        </#if>

        <button type="submit">Create poll</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>