<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/lectures/${lectureId}/questions/${questionId}" method="post">

        <p>Question ID: ${questionId}></p>

        <p>Lecture ID: ${lectureId}></p>

        <a href="/app/lectures/${lectureId}/questions">Questions</a>

        <label>User ID</label><br>
        <input type="text" placeholder="User ID" name="userId" required><br>

        <label>Question text</label><br>
        <input type="text" placeholder="Text" name="text"><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

    </form>

    <form action="/app/lectures/${lectureId}/questions/${questionId}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>