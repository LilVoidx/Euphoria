<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/izitoast/dist/css/iziToast.min.css">

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
<script src="https://cdn.jsdelivr.net/npm/izitoast/dist/js/iziToast.min.js"></script>
<script>
    // Get error message from model attribute
    var errorMessage = "${errorMessage}";

    // If there's an error message, display it using Izitoast
    if (errorMessage) {
        iziToast.error({
            title: 'Error',
            message: errorMessage,
            position: 'topCenter',
            backgroundColor: '#ff8181',
            timeout: 5000,
            layout: 2,
        });
    }
</script>
</body>
</html>
