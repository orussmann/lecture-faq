<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <div class="card bg-base-100 shadow-xl max-w-2xl mx-auto">
        <div class="card-body">
            <h2 class="card-title text-2xl mb-6">Polls</h2>
            <ul class="space-y-3">
                <#list polls as poll>
                    <li class="card bg-base-200">
                        <div class="card-body p-4">
                            <h3 class="font-bold text-lg">${poll.title}</h3>
                            <p class="text-sm opacity-70">${poll.description}</p>
                            <div class="card-actions justify-end mt-2">
                                <a href="/app/user/student/polls/${poll.id}" class="btn btn-sm btn-primary">Details</a>
                            </div>
                        </div>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
</@base.layout>