<#import "../base-layout-lecturer.ftlh" as base>
<@base.layout>
    <h1>${poll.title}</h1>
    <ul>
        <#list answers as answer>
            <li>
                ${answer.text}
            </li>
        </#list>
    </ul>
</@base.layout>
