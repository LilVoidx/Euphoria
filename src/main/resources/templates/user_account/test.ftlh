<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Artist Registration</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
</head>
<body>
<#include "components/navbar.ftlh">

<div class="container">
    <div class="ring register-ring">
        <i></i>
        <i></i>
        <i></i>
        <div class="register">
            <h2>Artist Registration</h2>
            <form action="/artists/register" method="post">
                <div class="inputBx">
                    <input type="text" id="stageName" name="stageName" placeholder="Stage Name" required>
                </div>
                <div class="inputBx">
                    <textarea id="bio" name="bio" rows="4" cols="40" placeholder="Bio" required></textarea>
                </div>
                <div class="inputBx">
                    <select name="genre" placeholder="Genre">
                        <option>Genre</option>
                        <#list genres as genre>
                            <option value="${genre}" style="color: white; background-color: #1d1d1d;">${genre}</option>
                        </#list>
                    </select>
                </div>
                <div class="inputBx">
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
    </div>
</div>

<script src="/static/js/passVisibility.js"></script>
</body>
</html>
