<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Album Details</title>
</head>
<body>
<h1>Album Details</h1>

<div>
    <h2>${album.title}</h2>
    <p><strong>Artist:</strong> <span>${album.artistName}</span></p>
    <p><strong>Release Date:</strong> <span>${album.releaseDate?string("yyyy-MM-dd")}</span></p>
    <p><strong>Genre:</strong> <span>${album.genre}</span></p>
    <img src="/files/img/${album.coverImageInfoUrl}" alt="Song Image">

    <h3>Songs</h3>
    <#--<ul>
        <li th:each="song : ${album.songs}">
            <span th:text="${song.title}"></span> - <span th:text="${song.duration}"></span>
        </li>
    </ul>-->
</div>
</body>
</html>
