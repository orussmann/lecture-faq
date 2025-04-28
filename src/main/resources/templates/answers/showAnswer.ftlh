<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <form action="/app/polls/${pollId}/answers/${answer.id}" method="post">

        <label>Answer text</label><br>
        <input type="text" value="${answer.text}" name="text" id="text" required><br>

        <label>Answer count</label>
        <input type="number" value="${answer.count}" name="count" id="count" min="0" required><br>

        <#--<input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>-->
        <button onclick="updateAnswerRestAPI()" type="button">Update via AJAX</button>

    </form>

    <form action="/app/polls/${pollId}/answers/${answer.id}" method="post">

        <#--<input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>-->

        <button onclick="deleteAnswer()" type="button">Delete via AJAX</button>

    </form>

    <script>
        function updateAnswerRestAPI() {
            const xhr = new XMLHttpRequest()
            const answer = {}
            answer.text = document.getElementById("text").value
            answer.count = document.getElementById("count").value
            console.log(answer)
            xhr.open("PUT", "/app/api/v1/polls/${pollId}/answers/${answer.id}")
            console.log("${pollId}")
            console.log("${answer.id}")
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify(answer))
        }


        function deleteAnswer() {
            const xhr = new XMLHttpRequest()
            xhr.open("DELETE", "/app/api/v1/polls/${pollId}/answers/${answer.id}")
            xhr.send()
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 204) {
                    location.href = "/app/polls/${pollId}/answers"
                }
            }
        }

    </script>
</@base.layout>