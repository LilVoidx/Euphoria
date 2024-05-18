<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
    <!-- Include iziToast CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/css/iziToast.min.css">
</head>
<body>
<#include "../components/navbar.ftlh">
<div class="container">
    <div class="ring login-ring">
        <i></i>
        <i></i>
        <i></i>
        <div class="login">
            <h2>Reset Password</h2>
            <form id="resetPasswordForm" action="/password/reset" method="post">
                <div class="inputBx">
                    <input type="hidden" id="code" name="code" value="${code}">
                </div>
                <div class="inputBx">
                    <input type="password" id="password" name="password" placeholder="New Password" required>
                </div>
                <div class="inputBx">
                    <input type="submit" value="Submit" id="submitButton">
                </div>
            </form>
            <div class="links" id="loginButtonContainer"></div>
        </div>
    </div>
</div>
<!-- Include iziToast JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/js/iziToast.min.js"></script>
<script src="/static/js/message.js"></script>
<script>
    // Display messages if any
    var resetPasswordMessage = "${resetPasswordMessage}";
    var resetPasswordErrorMessage = "${resetPasswordErrorMessage}";

    if (resetPasswordMessage && resetPasswordMessage.trim() !== "") {
        iziToast.success({
            title: 'Success',
            message: resetPasswordMessage,
            position: 'topRight'
        });
        // Disable the submit button and password input
        document.getElementById("submitButton").disabled = true;
        document.getElementById("password").disabled = true;

        // Create login link
        var loginButtonContainer = document.getElementById("loginButtonContainer");
        var loginLink = document.createElement("a");
        loginLink.textContent = "Login";
        loginLink.href = "/signIn";
        loginLink.classList.add("link");
        loginButtonContainer.appendChild(loginLink);
    }

    if (resetPasswordErrorMessage && resetPasswordErrorMessage.trim() !== "") {
        iziToast.error({
            title: 'Error',
            message: resetPasswordErrorMessage,
            position: 'topRight'
        });
    }
</script>

<#include "../components/footer.ftlh">
</body>
</html>
