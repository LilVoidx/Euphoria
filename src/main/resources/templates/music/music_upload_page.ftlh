<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Upload Song</title>
</head>
<body>
<h1>Upload Song</h1>

<form action="/upload-song" method="post" enctype="multipart/form-data">
    <div>
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>
    </div>
    <div>
        <label for="releaseDate">Release Date:</label>
        <input type="date" id="releaseDate" name="releaseDate" required>
    </div>
    <div>
        <label for="songFile">Song File:</label>
        <input type="file" id="songFile" name="songFile" accept=".mp3" required>
    </div>
    <div>
        <label for="imageFile">Image File:</label>
        <input type="file" id="imageFile" name="imageFile" accept="image/jpeg, image/png" required>
    </div>
    <div>
        <label for="genre">Genre:</label>
        <select id="genre" name="genre" placeholder="Genre">
            <option>Genre</option>
        </select>
    </div>

    <input type="hidden" id="userId" name="userId" value="${userId}">
    <button type="submit">Upload</button>
</form>

<#if successMessage?has_content>
    <p style="color: green;">${successMessage}</p>
</#if>

<#if errorMessage?has_content>
    <p style="color: red;">${errorMessage}</p>
</#if>
<script src="/static/js/genre.js"></script>
</body>
</html>
