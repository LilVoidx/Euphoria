<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Album</title>
</head>
<body>
<h1>Create Album</h1>


<form method="post" action="/albums/create" enctype="multipart/form-data">
    <div>
        <label for="title">Album Title:</label>
        <input type="text" id="title" name="title" required>
    </div>

    <div>
        <label for="releaseDate">Release Date:</label>
        <input type="date" id="releaseDate" name="releaseDate" required>
    </div>

    <div>
        <label for="genre">Genre:</label>
        <select id="genre" name="genre" placeholder="Genre">
            <option>Genre</option>
        </select>
    </div>


    <div>
        <label for="coverImage">Cover Image:</label>
        <input type="file" id="coverImage" name="coverImage" accept="image/jpeg, image/png" required>
    </div>

    <input type="hidden" id="userId" name="userId" value="${userId}">
    <button type="submit">Create Album</button>
</form>

<script src="/static/js/genre.js"></script>
</body>
</html>
