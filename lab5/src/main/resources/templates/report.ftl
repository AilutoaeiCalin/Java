<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${catalogName}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
            background-color: #f7f7f7;
        }

        h1 {
            color: #333333;
        }

        table {
            border-collapse: collapse;
            width: 100%;
            background: white;
        }

        th, td {
            border: 1px solid #cccccc;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #eaeaea;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        a {
            color: #1a73e8;
            text-decoration: none;
        }
    </style>
</head>
<body>
<h1>Catalog Report: ${catalogName}</h1>

<table>
    <tr>
        <th>ID</th>
        <th>Title</th>
        <th>Author</th>
        <th>Year</th>
        <th>Location</th>
        <th>Description</th>
    </tr>

    <#list resources as resource>
        <tr>
            <td>${resource.id}</td>
            <td>${resource.title}</td>
            <td>${resource.author}</td>
            <td>${resource.year}</td>
            <td>
                <#if resource.webResource>
                    <a href="${resource.location}">${resource.location}</a>
                <#else>
                    ${resource.location}
                </#if>
            </td>
            <td>${resource.description}</td>
        </tr>
    </#list>
</table>
</body>
</html>