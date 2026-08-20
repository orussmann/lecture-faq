<#import "../base-layout-student.ftlh" as base>

<@base.layout>
    <h2>Polls</h2>
    <ul>
        <#list polls as poll>
            <li>
                <a href="/app/user/student/polls/${poll.id}">
                    Poll ID: ${poll.id}, User: ${poll.userId}, Title: ${poll.title},
                    Description: ${poll.description}
                </a>
            </li>
        </#list>
    </ul>
</@base.layout>