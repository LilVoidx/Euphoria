document.addEventListener("DOMContentLoaded", function () {
    const miniPlayButton = document.getElementById('mini-play-button');
    const miniSongImage = document.getElementById('mini-song-image');
    const miniSongName = document.getElementById('mini-song-name');
    const miniArtistName = document.getElementById('mini-artist-name');
    const miniPlayerStatus = document.getElementById('mini-player-status');

    const audio = new Audio();
    let isPlaying = false;

    // Play and Pause
    miniPlayButton.addEventListener('click', function () {
        if (audio.paused) {
            audio.play();
            isPlaying = true;
            miniPlayButton.classList.remove('bx-play');
            miniPlayButton.classList.add('bx-pause');
            miniPlayerStatus.textContent = "Playing";
        } else {
            audio.pause();
            isPlaying = false;
            miniPlayButton.classList.add('bx-play');
            miniPlayButton.classList.remove('bx-pause');
            miniPlayerStatus.textContent = "Paused";
        }
    });

    // Update song information
    function updateSongInfo(song) {
        miniSongName.textContent = song.title;
        miniArtistName.textContent = song.artistName;
        miniSongImage.src = "/files/img/" + song.songImageInfoUrl;
        audio.src = "/files/img/" + song.songFileInfoUrl;
    }

    // Fetch song data from the server
    function fetchSongData(songId) {
        $.ajax({
            url: "/songData/" + songId,
            type: "GET",
            dataType: "json",
            success: function (songData) {
                updateSongInfo(songData);
                audio.play();
                isPlaying = true;
                miniPlayButton.classList.remove('bx-play');
                miniPlayButton.classList.add('bx-pause');
                miniPlayerStatus.textContent = "Playing";
            },
            error: function (xhr, status, error) {
                console.error('Error fetching song data:', error);
            }
        });
    }


    // Add event listener to right arrow icons
    const playIcons = document.querySelectorAll('.play-song');
    playIcons.forEach(icon => {
        icon.addEventListener('click', function () {
            const songId = this.dataset.songId;
            fetchSongData(songId);
        });
    });
});
