<#import "../layout.ftlh" as base>

<@base.layout>
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
    <p>Please provide at least <b>two</b> answers</p>
    <form action="/app/users/${userId}/polls" method="post">

        <label>Poll title</label><br>
        <input type="text" placeholder="Title" name="title"><br>

        <label>Poll description</label>
        <input type="text" placeholder="Description" name="description"><br>

        <label>Answer 1</label>
        <input type="text" placeholder="Answer 1" name="answer1"><br>

        <label>Answer 2</label>
        <input type="text" placeholder="Answer 2" name="answer2"><br>

        <label>Answer 3</label>
        <input type="text" placeholder="Answer 3" name="answer3"><br>

        <label>Answer 4</label>
        <input type="text" placeholder="Answer 4" name="answer4"><br>

        <label>Answer 5</label>
        <input type="text" placeholder="Answer 5" name="answer5"><br>

        <button type="submit">Create poll</button>
    </form>
    <br>
    <a href="">Back</a>
</@base.layout>