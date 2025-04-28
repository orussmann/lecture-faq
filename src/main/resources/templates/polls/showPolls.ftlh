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
    <form action="/app/users/${userId}/polls" method="post">

        <label>Poll title</label><br>
        <input type="text" placeholder="What is a web server?" name="title" required><br>

        <label>Poll description</label>
        <input type="text" placeholder="Describe the ins and outs of a web server..." name="description" required><br>

        <label>Answer 1</label>
        <input type="text" placeholder="It serves data to a web client.." name="answers[0]" required><br>

        <label>Answer 2</label>
        <input type="text" placeholder="Its communication is based on HTTP..." name="answers[1]" required><br>

        <label>Answer 3</label>
        <input type="text" placeholder="Answer 3" name="answers[2]"><br>

        <label>Answer 4</label>
        <input type="text" placeholder="Answer 4" name="answers[3]"><br>

        <label>Answer 5</label>
        <input type="text" placeholder="Answer 5" name="answers[4]"><br>

        <button type="submit">Create poll</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>