<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lecture FAQ</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<h2>Multiple-Choice-Umfragen</h2>
<ul>
    <#list polls as poll>
        <li>
            <a href="/app/public-polls/${poll.id}">
                Poll ID: ${poll.id}, User: ${poll.userId}, Title: ${poll.title},
                Description: ${poll.description}
            </a>
        </li>
    </#list>
</ul>
<a href="/app/">Back</a>
<footer>
    <a href="#">Imprint</a>
    <a href="#">About us</a>
    <a href="#">Support</a>
</footer>
</body>
</html>
