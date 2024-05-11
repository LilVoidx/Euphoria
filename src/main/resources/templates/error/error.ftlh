<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="robots" content="noindex, follow">
    <title>Error</title>
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/static/css/error/error.css">
</head>
<body>
<#include "components/navbar.ftlh">

<div id="notfound">
    <div class="notfound">
        <div class="notfound-404"></div>
        <h1>${statusCode}</h1>
        <h2>${errorMessage}</h2>
        <p>${errorDescription}</p>
        <a href="/home">Back to homepage</a>
    </div>
</div>


</body>
</html>
