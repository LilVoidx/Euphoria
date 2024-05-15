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

    let isPlaying = false;
    let isSeeking = false;
    let isSoundBoxOpen = false;

    // Update song information
    function updateSongInfo(song, autoplay = true) {
        songName.textContent = song.title;
        artistName.textContent = song.artistName;
        albumName.textContent = song.albumTitle;
        audio.src = "/files/img/" + song.songFileInfoUrl;
        songImage.src = "/files/img/" + song.songImageInfoUrl;
        if (autoplay) {
            audio.play();
            isPlaying = true;
            playButton.classList.remove('bxs-right-arrow');
            playButton.classList.add('bx-pause');
        }
    }

    // Fetch song data from the server
    function fetchSongData(songId) {
        $.ajax({
            url: "/songData/" + songId,
            type: "GET",
            dataType: "json",
            success: function(songData) {
                updateSongInfo(songData);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching song data:', error);
            }
        });
    }


    // Fetch random song data from the server
    function fetchRandomSong() {
        $.ajax({
            url: "/randomSong",
            type: "GET",
            dataType: "json",
            success: function(songData) {
                updateSongInfo(songData, false);
            },
            error: function(xhr, status, error) {
                console.error('Error fetching random song data:', error);
            }
        });
    }


    // Fetch song data from the server
    fetchRandomSong();


    // Fetch song data when "Listen Now" button is clicked
    const listenNowButton = document.querySelector('.play-song');
    listenNowButton.addEventListener('click', function () {
        const songId = this.closest('.trending-song-info').dataset.songId;
        fetch("/songData/" + songId)
            .then(response => response.json())
            .then(songData => {
                updateSongInfo(songData);
            })
            .catch(error => {
                console.error('Error fetching song data:', error);
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
        audio.volume = volumeSlider.value / 100;
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
