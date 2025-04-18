<#import "../layout.ftlh" as base>
<@base.layout>
    <form action="/app/polls/${pollId}/answers/${answer.id}" method="post">

        <label>Answer text</label><br>
        <input type="text" value="${answer.text}" name="text"><br>

        <label>Answer count</label>
        <input type="text" value="${answer.count}" name="count"><br>

        <input type="hidden" name="_method" value="PUT"/>
        <button>Update</button>


    </form>

    <form action="/app/polls/${pollId}/answers/${answer.id}" method="post">

        <input type="hidden" name="_method" value="DELETE"/>
        <button>Delete</button>

    </form>
</@base.layout>