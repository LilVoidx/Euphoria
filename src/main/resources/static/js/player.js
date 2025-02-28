document.addEventListener("DOMContentLoaded", function () {
    const audio = new Audio();
    const playButton = document.querySelector('.play-button');
    const currentTimeDisplay = document.querySelector('.current-time');
    const durationDisplay = document.querySelector('.duration');
    const activeLine = document.querySelector('.active-line');
    const progressBall = document.querySelector('.progress-ball');
    const songImage = document.getElementById('song-image');
    const songName = document.getElementById('song-name');
    const artistName = document.getElementById('artist-name');
    const albumName = document.getElementById('album-name');
    const progressBar = document.querySelector('.progress-bar');
    const soundBox = document.querySelector('.sound-box');
    const volumeSlider = document.querySelector('.volume-slider');
    const volumeUp = document.querySelector('.volume-up');
    const volumeDown = document.querySelector('.volume-down');
    const volumeButton = document.querySelector('.bx-volume-full');
    const playLikeButton = document.getElementById('playLikeButton');
    const likeButton = document.getElementById('likeButton');

    let isPlaying = false;
    let isSeeking = false;
    let isSoundBoxOpen = false;
    audio.volume = 0.5;

    // Update song information
    function updateSongInfo(song, isFavorite, autoplay = true) {
        songName.textContent = song.title;
        artistName.textContent = song.artistName;
        albumName.textContent = song.albumTitle? song.albumTitle: "Single";
        audio.src = "/files/img/" + song.songFileInfoUrl;
        songImage.src = "/files/img/" + song.songImageInfoUrl;

        if (autoplay) {
            audio.play();
            isPlaying = true;
            playButton.classList.remove('bxs-right-arrow');
            playButton.classList.add('bx-pause');
        }

        // Check if the song is favorited by the current user
        if (isFavorite) {
            playLikeButton.classList.add('highlighted');
            likeButton.classList.add('highlighted')

        } else {
            playLikeButton.classList.remove('highlighted');
            likeButton.classList.remove('highlighted')
        }

        // Set the song ID to the like button data attribute
        playLikeButton.setAttribute('data-song-id', song.songId);

        console.log(isFavorite)
        console.log(song.songId)
    }

    // Function to fetch song data from the server
    function fetchSongData(songId) {
        $.ajax({
            url: "/song/data/" + songId,
            type: "GET",
            dataType: "json",
            success: function(response) {
                const songData = response.song;
                const isFavorite = response.isFavorite;
                updateSongInfo(songData, isFavorite);
                console.log('Clicked song ID:', songId);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching song data:', error);
            }
        });
    }

    // Fetch song data when the page loads
    $(document).ready(function() {
        const url = window.location.pathname;
        const songIdFromUrl = url.split('/').pop();

        // If there is a song ID in the URL, fetch the song data by that ID
        if (songIdFromUrl && !isNaN(songIdFromUrl)) {
            fetchSongData(songIdFromUrl);
        }
    });


    // Fetch random song data from the server only if on the home page
    function fetchTrendingSong() {
        // Check if the current URL is the home page
        if (window.location.pathname === "/home") {
            $.ajax({
                url: "/song/trending",
                type: "GET",
                dataType: "json",
                success: function(response) {
                    const songData = response.song;
                    const isFavorite = response.isFavorite;
                    updateSongInfo(songData, isFavorite);
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching trending song data:', error);
                }
            });
        }
    }

    // Call fetchRandomSong() when the document is ready
    $(document).ready(function() {
        fetchTrendingSong();
    });



    // Fetch song data when "Listen Now" button is clicked
    const listenNowButton = document.querySelector('.play-song');
    listenNowButton.addEventListener('click', function () {
        const songId = this.closest('.trending-song-info').dataset.songId;
        fetch("/song/data/" + songId)
            .then(response => response.json())
            .then(songData => {
                updateSongInfo(songData);
            })
            .catch(error => {
                console.error('Error fetching song data:', error);
            });
    });

    // Fetch song data when "play Now" button is clicked
    document.querySelectorAll('.play-song').forEach(button => {
        button.addEventListener('click', function() {
            const songId = this.closest('.song-item').dataset.songId;
            fetchSongData(songId);
        });
    });

    // Play and Pause
    playButton.addEventListener('click', function () {
        if (audio.paused) {
            audio.play();
            isPlaying = true;
            playButton.classList.remove('bxs-right-arrow');
            playButton.classList.add('bx-pause');
        } else {
            audio.pause();
            isPlaying = false;
            playButton.classList.add('bxs-right-arrow');
            playButton.classList.remove('bx-pause');
        }
    });

    // Update progress bar and current time
    function updateProgress(e) {
        const rect = progressBar.getBoundingClientRect();
        let percent = ((e.clientX - rect.left) / progressBar.offsetWidth) * 100;
        if (percent < 0) percent = 0;
        if (percent > 100) percent = 100;
        const duration = (percent / 100) * audio.duration;
        audio.currentTime = duration;
        activeLine.style.width = percent + '%';
        progressBall.style.left = percent + '%';
        currentTimeDisplay.textContent = formatTime(duration);
    }

    // Drag and seek functionality for progress bar
    progressBar.addEventListener('mousedown', function (e) {
        isSeeking = true;
        updateProgress(e);
        window.addEventListener('mousemove', function (e) {
            if (isSeeking) {
                updateProgress(e);
            }
        });
        window.addEventListener('mouseup', function () {
            isSeeking = false;
        });
    });

    // Update progress bar and current time during playback
    audio.addEventListener('timeupdate', function () {
        if (!isSeeking) {
            const percent = (audio.currentTime / audio.duration) * 100;
            activeLine.style.width = percent + '%';
            progressBall.style.left = percent + '%';
            currentTimeDisplay.textContent = formatTime(audio.currentTime);
        }
    });

    // Format time function
    function formatTime(seconds) {
        let min = Math.floor(seconds / 60);
        let sec = Math.floor(seconds % 60);
        min = min < 10 ? '0' + min : min;
        sec = sec < 10 ? '0' + sec : sec;
        return min + ':' + sec;
    }

    // Set duration
    audio.addEventListener('loadedmetadata', function () {
        durationDisplay.textContent = formatTime(audio.duration);
    });

    // Volume controls
    volumeUp.addEventListener('click', function () {
        if (audio.volume < 1) {
            audio.volume += 0.1;
            volumeSlider.value = audio.volume * 100;
        }
    });

    volumeDown.addEventListener('click', function () {
        if (audio.volume > 0) {
            audio.volume -= 0.1;
            volumeSlider.value = audio.volume * 100;
        }
    });

    volumeSlider.addEventListener('input', function () {
        audio.volume = volumeSlider.value / 200;
    });

    // Toggle sound box visibility and highlight volume button
    volumeButton.addEventListener('click', function () {
        isSoundBoxOpen = !isSoundBoxOpen;
        soundBox.style.display = isSoundBoxOpen ? 'flex' : 'none';
        volumeButton.classList.toggle('highlighted', isSoundBoxOpen);
    });

    // Close sound box if clicked outside
    document.addEventListener('click', function (event) {
        if (!event.target.closest('.sound-box') && event.target !== volumeButton) {
            soundBox.style.display = 'none';
            volumeButton.classList.remove('highlighted');
            isSoundBoxOpen = false;
        }
    });

    // Prevent double click to open sound box
    volumeButton.addEventListener('dblclick', function (event) {
        event.preventDefault();
    });

    // Add click event listener to each song item
    const songItems = document.querySelectorAll('.song-item');
    songItems.forEach(songItem => {
        songItem.addEventListener('click', function () {
            const songId = this.dataset.songId;
            fetchSongData(songId);
        });
    });
});

// Define a function to handle liking/unliking a song
function playLikeSong(songId) {
    console.log("song : " + songId)
    // Send request to like/unlike the song
    $.ajax({
        url: "/song/" + songId + "/favorite",
        type: "POST",
        dataType: "json",
        success: function(response) {
            // Extract the song and isFavorite from the response
            const song = response.song;

            // Get the current text (title and like count) of the likeCount element
            const likeCountElement = document.getElementById('likeCount');

            // Update the like count on the front end while preserving the song title
            likeCountElement.textContent = `${song.likeCount} Million Favorites`;

            // Update the appearance of the like button based on isFavorite status
            const playLikeButton = document.getElementById('playLikeButton');
            playLikeButton.classList.toggle('highlighted');

            const likeButton = document.getElementById('likeButton');
            likeButton.classList.toggle('highlighted');
        },
        error: function(xhr, status, error) {
            console.error('Error liking the song:', error);
        }
    });
}

// Add event listener to the heart icon
document.getElementById('playLikeButton').addEventListener('click', function() {
    // Get the song ID from the data attribute of the heart icon
    const songId = this.getAttribute('data-song-id');

    // Call the likeSong function with the songId
    playLikeSong(songId);
});

