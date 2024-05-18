<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Profile Picture</title>
    <link rel="stylesheet" href="/static/css/auth/auth.css">
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<#include "components/navbar.ftlh">

<div class="container">
    <div class="ring login-ring">
        <i></i>
        <i></i>
        <i></i>
        <div class="register">
            <h2>Upload Profile Picture</h2>
            <form id="profilePictureForm" enctype="multipart/form-data">
                <input type="hidden" id="userId" name="userId" value="${userId}">

                <div class="inputBx">
                    <input type="file" id="profilePicture" name="profilePicture" accept=".jpg, .jpeg, .png" required>
                </div>

                <div class="inputBx">
                    <input type="button" value="Upload" onclick="uploadProfilePicture()">
                </div>
            </form>
        </div>
    </div>
</div>

<script src="/static/js/upload_picture.js"></script>


</body>
</html>
