<!DOCTYPE html>
<html>
<head>
    <title>Album Details</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/izimodal/1.6.0/css/iziModal.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Album Details</h1>
    <h2>${album.title}</h2>
    <p>Artist: ${album.artistName}</p>
    <p>Release Date: ${album.releaseDate?string["yyyy-MM-dd"]}</p>
    <img src="/files/img/${album.coverImageInfoUrl}" alt="Album Cover" class="img-fluid"/>

    <h3>Songs by ${album.artistName}</h3>
    <table class="table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Genre</th>
            <th>Duration</th>
            <th>Release Date</th>
            <th>Image</th>
            <th>Add to Album</th>
        </tr>
        </thead>
        <tbody>
        <#list artistSongDTOs as song>
            <tr>
                <td>${song.title}</td>
                <td>${song.genre}</td>
                <td>${song.duration}</td>
                <td><span class="badge badge-secondary">${song.releaseDate?string["yyyy-MM-dd"]}</span></td>
                <td><img src="/files/img/${song.songImageInfoUrl}" alt="${song.title}" class="img-thumbnail" style="max-width: 100px;"></td>
                <td>
                    <button class="btn btn-primary add-song" data-song-id="${song.songId}" data-album-id="${album.albumId}" data-user-id="${user.username}">Add to Album</button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</div>

<div id="successModal" data-izimodal-transitionin="fadeInDown" data-izimodal-group="alerts">
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/izimodal/1.6.0/js/iziModal.min.js"></script>
<script>
    // Initialize iziModal for success alert
    $('#successModal').iziModal({
        icon: 'icon-check',
        headerColor: '#00af66',
        backdrop : '',
        width: 600,
        timeout: 15000,
        timeoutProgressbar: true,
        transitionIn: 'fadeInDown',
        transitionOut: 'fadeOutDown',
        pauseOnHover: true,
    });

    $(document).ready(function() {
        $('.add-song').click(function() {
            var songId = $(this).data('song-id');
            var albumId = $(this).data('album-id');
            var username = $(this).data('user-id');
            $.ajax({
                type: 'POST',
                url: '/albums/' + username + '/album/' + albumId + '/addSong',
                data: { songId: songId },
                success: function() {
                    $('#successModal').iziModal('setTitle', 'Song Added');
                    $('#successModal').iziModal('setSubtitle', 'Song has been added to the album successfully!');
                    $('#successModal').iziModal('open');
                },
                error: function() {
                    alert('Error adding song to the album!');
                }
            });
        });
    });
</script>
</body>
</html>
