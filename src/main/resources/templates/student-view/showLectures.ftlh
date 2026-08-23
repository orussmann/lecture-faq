<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">My Lectures</h2>
            <ul class="space-y-3">
                <#list allLectures as lecture>
                    <li class="card bg-base-200">
                        <div class="card-body p-4">
                            <h3 class="font-bold text-lg">${lecture.title}</h3>
                            <div class="text-sm space-y-1">
                                <p><strong>Typ:</strong> ${lecture.type}</p>
                                <p><strong>Beschreibung:</strong> ${lecture.description}</p>
                                <p><strong>Link:</strong> <a href="${lecture.link}" class="link link-primary">${lecture.link}</a></p>
                                <p><strong>Vorlesungscode:</strong> ${lecture.code?c}</p>
                            </div>
                            <div class="card-actions justify-end mt-2">
                                <a href="/app/user/student/lectures/${lecture.id}" class="btn btn-sm btn-primary">Details</a>
                            </div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
    <script>
        document.addEventListener("DOMContentLoaded", () => {
            document.querySelectorAll("button").forEach(btn => {
                btn.addEventListener("click", () => {
                    const lectureId = btn.id;
                    const xhr = new XMLHttpRequest();
                    xhr.open("PUT", "/app/api/v1/users/${userId}/lectures/" + lectureId);
                    xhr.setRequestHeader("Content-Type", "application/json");
                    xhr.send(JSON.stringify(""));   // TODO
                });
            });
        });
    </script>
</@base.layout>