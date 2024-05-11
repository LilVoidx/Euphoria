<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
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
            <h2>Forgot Password</h2>
            <form action="/password/forgot" method="post">
                <div class="inputBx">
                    <input type="email" id="email" name="email" placeholder="Email" required>
                </div>
                <div class="inputBx">
                    <input type="submit" value="Submit" id="submitButton">
                </div>
            </form>
            <div id="forgotPasswordMessage" class="form-messages form-success">${forgotPasswordMessage}</div>
            <div id="forgotPasswordErrorMessage" class="form-messages form-error">${forgotPasswordErrorMessage}</div>
        </div>
    </div>
</div>

<script src="/static/js/message.js"></script>
<script>
    // Display messages if any
    showMessage("forgotPasswordMessage", "${forgotPasswordMessage}");
    showMessage("forgotPasswordErrorMessage", "${forgotPasswordErrorMessage}");
</script>
<script>
    // Disable form elements after successful submission
    var successMessage = "${forgotPasswordMessage}";
    var submitButton = document.getElementById("submitButton");
    var emailInput = document.getElementById("email");

    if (successMessage && successMessage.trim() !== "") {
        submitButton.disabled = true;
        emailInput.disabled = true;
    }
</script>

<#include "../components/footer.ftlh">
</body>
</html>
