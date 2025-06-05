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
<h2>Public Lectures</h2>
<ul>
    <#list allLectures as lecture>
        <li>
            <a href="/app/public-lectures/${lecture.id}">
                <h3>Name:</h3>
                <p>${lecture.title}</p>
                <h3>Typ:</h3>
                <p>${lecture.type}</p><br>
                <h3>Beschreibung:</h3>
                <p>${lecture.description}</p><br>
                <h3>Link:</h3>
                <p>${lecture.link}</p>
                <h3>Vorlesungscode:</h3>
                <p>Lecture code</p>: ${lecture.code?c}
            </a>
            <button type="button" data-lecture-id="${lecture.id}">Beitreten</button>
        </li>
    </#list>
</ul>
<footer>
    <a href="#">Imprint</a>
    <a href="#">About us</a>
    <a href="#">Support</a>
</footer>
</body>
</html>
