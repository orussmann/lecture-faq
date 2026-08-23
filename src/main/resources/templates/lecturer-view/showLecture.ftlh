<#import "../base-layout-lecturer.ftlh" as base>
<@base.layout>
    <#if notification??>
        <div class="alert alert-info max-w-4xl mx-auto mb-4">
            <span>${notification}</span>
        </div>
    </#if>

    <div class="card bg-base-100 shadow-xl max-w-4xl mx-auto mb-8">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">${lecture.title}</h2>
            <div class="card bg-base-200 mb-4">
                <div class="card-body p-4">
                    <h3 class="font-bold text-lg mb-4">Live Chat</h3>
                    <div id="chat" class="bg-base-300 p-4 rounded-lg h-96 overflow-y-auto mb-4 space-y-2">
                        <#list chatMessages as chatMessage>
                            <div class="chat chat-start">
                                <div class="chat-bubble chat-bubble-primary">
                                    <div class="chat-header">
                                        <strong>${chatMessage.chatUserName}</strong>
                                        <time class="text-xs opacity-50">${chatMessage.createdAt}</time>
                                    </div>
                                    <p>${chatMessage.text}</p>
                                </div>
                            </div>
                        </#list>
                    </div>
                    
                    <div class="flex gap-2">
                        <input type="text" id="username" placeholder="Benutzername... (optional)" class="input input-bordered w-1/3" />
                        <input type="text" id="text" placeholder="Nachricht..." class="input input-bordered flex-grow" />
                        <button onclick="sendMessage()" class="btn btn-primary">Send</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        let ws;
        let message = {};

        function sendMessage() {
            message.lectureId = "${lecture.id}"
            message.userId = "${userId}"
            let username = document.getElementById("username").value
            message.chatUserName = username.length > 0 ? username : "Anonymer Absender"
            message.text = document.getElementById("text").value
            message.createdAt = new Date().toISOString()
            ws.send(JSON.stringify(message))
            document.getElementById("text").value = ""
        }

        document.addEventListener("DOMContentLoaded", () => {
            ws = new WebSocket("/app/chat?roomId=" + "${lecture.id}")
            let chat = document.getElementById("chat")

            ws.onmessage = function (msg) {
                message = JSON.parse(msg.data)  // TODO: Fix time formatting -> different format when reloading the page and when receiving new message (also for student view)
                const chatBubble = document.createElement("div")
                chatBubble.className = "chat chat-start"
                <#noparse>
                chatBubble.innerHTML = `
                    <div class="chat-bubble chat-bubble-primary">
                        <div class="chat-header">
                            <strong>${message.chatUserName}</strong>
                            <time class="text-xs opacity-50">${message.createdAt}</time>
                        </div>
                        <p>${message.text}</p>
                    </div>
                `
                </#noparse>
                chat.appendChild(chatBubble)
                chat.scrollTop = chat.scrollHeight
            }
        })
    </script>
<#--Lower section is only for the lecturer/ admin view-->

    <div class="card bg-base-100 shadow-xl max-w-4xl mx-auto mb-8">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Lecture bearbeiten</h2>
            <form action="/app/user/lectures/${lecture.id}" method="post" class="space-y-4">

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture ID</span>
                    </label>
                    <input type="text" value="${lecture.id}" disabled class="input input-bordered w-full bg-base-200" />
                </div>

                <div class="form-control">
                    <a href="/app/lectures/${lecture.id}/questions" class="btn btn-outline w-full">Questions</a>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture title</span>
                    </label>
                    <input type="text" value="${lecture.title}" placeholder="FDDW" name="title" id="title" required class="input input-bordered w-full" />
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture description</span>
                    </label>
                    <input type="text" value="${lecture.description}" placeholder="A course that focuses on web services"
                           name="description" id="description" required class="input input-bordered w-full" />
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture type</span>
                    </label>
                    <select name="type" id="type" required class="select select-bordered w-full">
                        <option value="ONSITE" <#if lecture.type == "ONSITE">selected</#if>>OnSite</option>
                        <option value="ONLINE" <#if lecture.type == "ONLINE">selected</#if>>Online</option>
                        <option value="HYBRID" <#if lecture.type == "HYBRID">selected</#if>>Hybrid</option>
                    </select>
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture link</span>
                    </label>
                    <input type="url"
                           value="${lecture.link}"
                           placeholder="https://test.com"
                           name="link"
                           id="link"
                           required
                           class="input input-bordered w-full"
                    />
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">User ID</span>
                    </label>
                    <input type="text"
                           value="${userId}"
                           placeholder="12345678-abcd-efgh-1234-12aB34Cd56eF"
                           name="userId"
                           id="userId"
                           pattern="^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$"
                           required
                           class="input input-bordered w-full"
                    />
                </div>

                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Lecture code</span>
                    </label>
                    <input type="number" value="${lecture.code?c}" placeholder="123" name="code" id="code" min="0" max="32767"
                           required class="input input-bordered w-full" />
                </div>

                <input type="hidden" name="_method" value="PUT"/>
                <div class="form-control mt-6">
                    <button type="submit" class="btn btn-primary w-full">Update</button>
                </div>

<#--        <button onclick="updateLectureRestAPI()" type="button">Update via AJAX</button>-->
            </form>
        </div>
    </div>

    <div class="card bg-base-100 shadow-xl max-w-4xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6 text-error">Lecture löschen</h2>
            <form action="/app/user/lectures/${lecture.id}" id="deletion-form" method="post">

                <input type="hidden" name="_method" value="DELETE"/>
                <div class="form-control">
                    <button id="delete-button" class="btn btn-error w-full">Delete</button>
                </div>

<#--        <button onclick="deleteLecture()" type="button">Delete via AJAX</button>-->
            </form>
            <div id="deletion-confirmation" hidden class="mt-4">
                <div class="alert alert-warning">
                    <label class="label">
                        <span class="label-text">Bitte bestätigen Sie, dass Sie diese Lecture löschen möchten. Geben Sie ${randomString} ein:</span>
                    </label>
                    <input type="text" id="confirmation-input" class="input input-bordered w-full mt-2" />
                    <button id="confirmation-button" class="btn btn-error mt-2">Confirm</button>
                </div>
            </div>
        </div>
    </div>
    <script>

        let notification = document.getElementById("notification")
        const deletionForm = document.querySelector("#deletion-form");
        const deleteButton = document.getElementById("delete-button");
        const deletionConfirmation = document.getElementById("deletion-confirmation");
        const conformationButton = document.getElementById("confirmation-button");

        if (notification){
            setTimeout(() => {
                notification.hidden = true;
            }, 3000)
        }

        document.addEventListener("DOMContentLoaded", () => {
            const form = document.querySelector("#deletion-form");
            form.addEventListener("submit", (event) => {
                event.preventDefault();
            });
        });

        const confirmationInput = document.getElementById("confirmation-input")
        confirmationInput.addEventListener("input", () => {
            const input = confirmationInput.value;
            const expected = "${randomString}";
            if (input === expected) {
                conformationButton.addEventListener("click", () => {
                    deletionForm.submit();
                })
            }
        });

        deleteButton.addEventListener("click", () => {
            deletionConfirmation.hidden = false;
        });


        //TODO: RegEx for pattern should be updated -> it's not a valid regex
        function updateLectureRestAPI() {
            const xhr = new XMLHttpRequest()
            const lecture = {}
            lecture.title = document.getElementById("title").value
            lecture.description = document.getElementById("description").value
            lecture.type = document.getElementById("type").value
            lecture.link = document.getElementById("link").value
            lecture.userId = document.getElementById("userId").value
            lecture.code = document.getElementById("code").value
            console.log(lecture)
            xhr.open("PUT", "/app/api/v1/users/${userId}/lectures/${lecture.id}")
            console.log("${userId}")
            console.log("${lecture.id}")
            xhr.setRequestHeader("Content-Type", "application/json")
            xhr.send(JSON.stringify(lecture))
        }


        function deleteLecture() {
            const xhr = new XMLHttpRequest()
            xhr.open("DELETE", "/app/api/v1/users/${userId}/lectures/${lecture.id}")
            xhr.send()
            xhr.onreadystatechange = function () {
                if (this.readyState === 4 && this.status === 204) {
                    location.href = "/app/user/lectures"
                }
            }
        }

    </script>
</@base.layout>