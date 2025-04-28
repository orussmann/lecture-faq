<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h1>Answers</h1>
    <ul>
        <#list answers as answer>
            <li>
                <a href="/app/polls/${answer.pollId}/answers/${answer.id}">
                    Answer ID: ${answer.id}, Poll ID: ${answer.pollId}, Text: ${answer.text},
                    Count: ${answer.count}
                </a>
            </li>
        </#list>
    </ul>
    <form action="/app/polls/${pollId}/answers" method="post">
        <input type="text" name="text" placeholder="This is an answer" required>
        <button>Create answer</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>
