<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <form action="/app/lectures/${lectureId}/questions/${questionId}" method="post">

        <p>Question ID: ${questionId}</p>

        <p>Lecture ID: ${lectureId}</p>

        <a href="/app/lectures/${lectureId}/questions">Questions</a>

        <label>User ID</label><br>
        <input type="text"
               value="${question.userId}"
               name="userId"
               id="userId"
               pattern="^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
               required
        ><br>

        <label>Question text</label><br>
        <input type="text" value="${question.text}" name="text" id="text" required><br>

        <#--<input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>-->

        <button onclick="updateQuestionRestAPI()" type="button">Update via AJAX</button>
    </form>

    <form action="/app/lectures/${lectureId}/questions/${questionId}" method="post">

        <#--<input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>-->

        <button onclick="deleteQuestion()" type="button">Delete via AJAX</button>
    </form>

    <script>
        function updateQuestionRestAPI() {
            const xhr = new XMLHttpRequest()
            const question = {}
            question.userId = document.getElementById("userId").value
            question.text = document.getElementById("text").value
            console.log(question)
            xhr.open("PUT", "/app/api/v1/lectures/${lectureId}/questions/${questionId}")
            console.log("${lectureId}")
            console.log("${questionId}")
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify(question))
        }


        function deleteQuestion() {
            const xhr = new XMLHttpRequest()
            xhr.open("DELETE", "/app/api/v1/lectures/${lectureId}/questions/${questionId}")
            xhr.send()
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 204) {
                    location.href = "/app/lectures/${lectureId}/questions"
                }
            }
        }

    </script>
</@base.layout>