<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SignUp</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/izitoast/dist/css/iziToast.min.css">
</head>
<body>
<#include "components/navbar.ftlh">


<div class="container">
    <div class="ring register-ring">
        <i></i>
        <i></i>
        <i></i>
        <div class="register">
            <h2>SignUp</h2>
            <form action="/signUp" method="post" enctype="multipart/form-data">

                <div class="inputBx">
                    <input type="text" id="username" name="username" placeholder="Username" required>
                </div>

                <div class="inputBx">
                    <input type="email" id="email" name="email" placeholder="Email" required>
                </div>

                <div class="inputBx">
                    <input type="password" id="passwordInput" name="password" placeholder="Password" required>
                    <span class="toggle-password" onclick="togglePasswordVisibility()"></span>
                </div>

                <div class="inputBx">
                    <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
                    <span class="toggle-confirm-password" onclick="toggleConfirmPasswordVisibility()"></span>
                </div>

                <div class="inputBx">
                    <input type="text" id="firstName" name="firstName" placeholder="First Name" required>
                </div>

                <div class="inputBx">
                    <input type="text" id="lastName" name="lastName" placeholder="Last Name" required>
                </div>

                <div class="inputBx">
                    <input type="text" id="phoneNumber" name="phoneNumber" placeholder="Phone Number">
                </div>

                <div class="inputBx">
                    <input type="submit" value="SignUp">
                </div>

            </form>
            <div class="links">
                <span class="already-registered">Already registered?</span>
                <a href="/signIn">Sign in</a>
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
            timeout: 3000,
            layout: 2,
        });
    }
</script>
</body>
</html>