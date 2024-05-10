<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${song.title}</title>
</head>
<body>
<h1>${song.title}</h1>
<p>Artist: ${song.artistName}</p>
<p>Genre: ${song.genre}</p>
<img src="/files/img/${song.songImageInfoUrl}" alt="Song Image">
<p>Release Date: ${song.releaseDate?string("yyyy-MM-dd")}</p>

<audio controls>
    <source src="/files/img/${song.songFileInfoUrl}" type="audio/mpeg">
    Your browser does not support the audio element.
</audio>
</body>
</html>
