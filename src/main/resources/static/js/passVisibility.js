function togglePasswordVisibility() {
    var passwordInput = document.getElementById("passwordInput");
    var passwordIcon = document.querySelector(".toggle-password");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        passwordIcon.style.backgroundImage = "url('/static/files/assets/icons8-closed-eye-50.png')";
    } else {
        passwordInput.type = "password";
        passwordIcon.style.backgroundImage = "url('/static/files/assets/icons8-surprise-64.png')";
    }
}

function toggleConfirmPasswordVisibility() {
    var passwordInput = document.getElementById("confirmPassword");
    var passwordIcon = document.querySelector(".toggle-confirm-password");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        passwordIcon.style.backgroundImage = "url('/static/files/assets/icons8-closed-eye-50.png')";
    } else {
        passwordInput.type = "password";
        passwordIcon.style.backgroundImage = "url('/static/files/assets/icons8-surprise-64.png')";
    }
}
