<#--
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${song.title}</title>
</head>
<body>
<h1>${song.title}</h1>
<p>Artist: ${song.artist.stageName}</p>
<p>Genre: ${song.genre}</p>
<audio controls>
    <source src="/song/${song.songId}/stream" type="audio/mpeg">
</audio>
</body>
</html>
-->
