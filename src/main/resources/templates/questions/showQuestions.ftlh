<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h1>Questions</h1>
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
        <input type="text" placeholder="bdcc1a19-dd83-4d63-8891-6e63cfe2e580" name="userId" required><br>

        <label>Question text</label><br>
        <input type="text" placeholder="Text" name="text" required><br>

        <button type="submit">Create question</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>
