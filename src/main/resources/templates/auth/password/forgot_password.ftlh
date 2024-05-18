<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
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
            <h2>Forgot Password</h2>
            <form action="/password/forgot" method="post">
                <div class="inputBx">
                    <input type="email" id="email" name="email" placeholder="Email" required>
                </div>
                <div class="inputBx">
                    <input type="submit" value="Submit" id="submitButton">
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/js/iziToast.min.js"></script>
<script>
    // Display messages if any
    var forgotPasswordMessage = "${forgotPasswordMessage}";
    var forgotPasswordErrorMessage = "${forgotPasswordErrorMessage}";

    if (forgotPasswordMessage && forgotPasswordMessage.trim() !== "") {
        iziToast.success({
            title: 'Success',
            message: forgotPasswordMessage,
            position: 'topRight'
        });
        // Disable the submit button
        document.getElementById("submitButton").disabled = true;
        document.getElementById("email").disabled = true;
    }

    if (forgotPasswordErrorMessage && forgotPasswordErrorMessage.trim() !== "") {
        iziToast.error({
            title: 'Error',
            message: forgotPasswordErrorMessage,
            position: 'topRight'
        });
    }
</script>


<#include "../components/footer.ftlh">
</body>
</html>
