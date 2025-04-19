<#import "../layout.ftlh" as base>

<@base.layout>
    <h2>Questions</h2>
    <ul>
        <#list questions as question>
            <li>
                <a href="/app/lectures/${question.lectureId}/questions/${question.id}">
                    Question ID: ${question.id},
                    Lecture ID: ${question.lectureId},
                    User ID: ${question.userId},
                    Text: ${question.text}
                </a>
            </li>
        </#list>
    </ul>
    <form action="/app/lectures/${lectureId}/questions" method="post">

        <label>User ID</label><br>
        <input type="text" placeholder="User ID" name="userId"><br>

        <label>Question text</label><br>
        <input type="text" placeholder="Text" name="text"><br>

        <button type="submit">Create question</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>
