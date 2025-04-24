<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/users/${poll.userId}/polls/${poll.id}" method="post">

        <p>Poll ID: ${poll.id}</p>

        <p>User ID: ${poll.userId}</p>

        <a href="/app/polls/${poll.id}/answers">Answers</a>

        <label>Poll title</label><br>
        <input type="text" value="${poll.title}" name="title" id="title" required><br>

        <label>Poll description</label>
        <input type="text" value="${poll.description}" name="description" id="description" required><br>

        <#--<input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>-->
        <button onclick="updatePollRestAPI()" type="button">Update via AJAX</button>
    </form>

    <form action="/app/users/${poll.userId}/polls/${poll.id}" method="post">

        <#--<input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>-->
        <button onclick="deletePoll()" type="button">Delete via AJAX</button>
    </form>

    <script>
        function updatePollRestAPI() {
            const xhr = new XMLHttpRequest()
            const poll = {}
            poll.title = document.getElementById("title").value
            poll.description = document.getElementById("description").value
            console.log(poll)
            xhr.open("PUT", "/app/api/v1/users/${poll.userId}/polls/${poll.id}")
            console.log("${poll.userId}")
            console.log("${poll.id}")
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify(poll))
        }


        function deletePoll() {
            const xhr = new XMLHttpRequest()
            xhr.open("DELETE", "/app/api/v1/users/${poll.userId}/polls/${poll.id}")
            xhr.send()
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 204) {
                    location.href = "/app/users/${poll.userId}/polls"
                }
            }
        }

    </script>
</@base.layout>