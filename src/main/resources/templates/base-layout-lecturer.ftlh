<#macro layout>
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
    <header>
        <nav>
            <a href="">Profil</a>
            <a href="">Meine Vorlesungen</a>
            <a href="">Multiple-Choice-Fragen</a>
        </nav>
    </header>
    <hr>
    <main>
        <#nested>
    </main>
    <hr>
    <footer>
        <a href="#">Imprint</a>
        <a href="#">About us</a>
        <a href="#">Support</a>
    </footer>
    </body>
    </html>
</#macro>