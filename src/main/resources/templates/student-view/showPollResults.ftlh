<#import "../base-layout-student.ftlh" as base>
<@base.layout>
    <h1>${title}</h1>
<#--    <div id="answers"></div>-->
    <div>
        <canvas id="myChart"></canvas>
    </div>

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
                }<#if answer_has_next>,</#if>
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
</@base.layout>