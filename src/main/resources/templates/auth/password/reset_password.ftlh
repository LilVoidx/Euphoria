<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
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
            <div id="resetPasswordMessage" class="form-messages form-success">${resetPasswordMessage}</div>
            <div id="resetPasswordErrorMessage" class="form-messages form-error">${resetPasswordErrorMessage}</div>
            <div class="links" id="loginButtonContainer"></div>
        </div>
    </div>
</div>
<script src="/static/js/message.js"></script>
<script>
    // Display messages if any
    showMessage("resetPasswordMessage", "${resetPasswordMessage}");
    showMessage("resetPasswordErrorMessage", "${resetPasswordErrorMessage}");

    // Disable form elements after successful submission
    var successMessage = "${resetPasswordMessage}";
    var submitButton = document.getElementById("submitButton");
    var passwordInput = document.getElementById("password");

    if (successMessage && successMessage.trim() !== "") {
        submitButton.disabled = true;
        passwordInput.disabled = true;

        // Create login link
        var loginButtonContainer = document.getElementById("loginButtonContainer");
        var loginLink = document.createElement("a");
        loginLink.textContent = "Login";
        loginLink.href = "/signIn";
        loginLink.classList.add("link");
        loginButtonContainer.appendChild(loginLink);
    }
</script>

<#include "../components/footer.ftlh"> <!-- Include Footer Component -->
</body>
</html>
