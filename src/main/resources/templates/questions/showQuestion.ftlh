<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/lectures/${lectureId}/questions/${questionId}" method="post">

        <p>Question ID: ${questionId}</p>

        <p>Lecture ID: ${lectureId}</p>

        <a href="/app/lectures/${lectureId}/questions">Questions</a>

        <label>User ID</label><br>
        <input type="text"
               value="${question.userId}"
               name="userId"
               pattern="^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
               required
        ><br>

        <label>Question text</label><br>
        <input type="text" value="${question.text}" name="text" required><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>

    </form>

    <form action="/app/lectures/${lectureId}/questions/${questionId}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>