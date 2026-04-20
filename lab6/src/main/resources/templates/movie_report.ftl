<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Movies Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 30px;
        }

        h1 {
            text-align: center;
        }

        table {
            border-collapse: collapse;
            width: 100%;
        }

        th, td {
            border: 1px solid #444;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
<h1>Movies Report</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Release Date</th>
        <th>Duration</th>
        <th>Score</th>
        <th>Genre</th>
    </tr>

    <#list movies as movie>
        <tr>
            <td>${movie.id}</td>
            <td>${movie.title}</td>
            <td>${movie.releaseDate}</td>
            <td>${movie.duration}</td>
            <td>${movie.score}</td>
            <td>${movie.genreName!"-"}</td>
        </tr>
    </#list>
</table>

</body>
</html>