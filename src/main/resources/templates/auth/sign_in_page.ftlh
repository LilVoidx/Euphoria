<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
</head>
<body>
<#include "components/navbar.ftlh">

<div class="container">
    <div class="ring login-ring">
        <i></i>
        <i></i>
        <i></i>
        <div class="login">
            <h2>Login</h2>
            <form action="/signIn" method="post">
                <div class="inputBx">
                    <input type="text" id="username" name="username" placeholder="Username" required>
                </div>
                <div class="inputBx">
                    <input type="password" id="passwordInput" name="password" placeholder="Password" required>
                    <span class="toggle-password" onclick="togglePasswordVisibility()"></span>
                </div>
                <div class="inputBx">
                    <input type="submit" value="Sign in">
                </div>
            </form>
            <div class="links">
                <a href="/password/forgot">Forgot Password?</a>
                <a href="/signUp">Signup</a>
            </div>
        </div>
    </div>
</div>
<script src="/static/js/passVisibility.js"></script>
</body>
</html>
