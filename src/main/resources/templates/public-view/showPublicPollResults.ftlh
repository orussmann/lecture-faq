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
<h1>${title}</h1>
<#--    <div id="answers"></div>-->
<div>
    <canvas id="myChart"></canvas>
</div>
<a href="/app/">Back</a>

<script>
    let chart;
    document.addEventListener("DOMContentLoaded", () => {

        const ctx = document.getElementById('myChart');
        chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: [],
                datasets: [{
                    label: '# of Votes',
                    data: [],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });

        const ws = new WebSocket("/app/poll-completion?pollId=${pollId}&role=STUDENT") //TODO: First draft, change later (also the other ws endpoints)...
        const answersArray = [
            <#list answers as answer>
            {
                "text": "${answer.text?js_string}",
                "count": ${answer.count}
            }<#if answer_has_next>, </#if>
            </#list>
        ];
        updateChart(answersArray);
        ws.onmessage = function (msg) {
            console.log("Data received: " + JSON.parse(msg.data));
            const jsonResponse = JSON.parse(msg.data);
            /*const answersDisplay = document.querySelector("#answers");
            jsonResponse.forEach(answer => {
                console.log("Text: " + answer.text);
                console.log("Count: " + answer.count);
                answersDisplay.innerHTML += "<p><b>" + answer.text + "</b><br>Votes: " + answer.count + "</p>";
            });*/
            updateChart(jsonResponse);
        };

        ws.onerror = function (error) {
            console.log("Error: " + error)
        }

        ws.onclose = function (msg) {
            console.log("Closing connection: " + msg.reason)
        }
    });

    function updateChart(jsonResponse) {
        chart.data.labels = [...(jsonResponse.map(obj => obj.text))];
        chart.data.datasets[0].data = [...(jsonResponse.map(obj => obj.count))];
        chart.update();
    }

</script>
<footer>
    <a href="#">Imprint</a>
    <a href="#">About us</a>
    <a href="#">Support</a>
</footer>
</body>
</html>