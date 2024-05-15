<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/static/css/music/song_upload.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/izimodal/1.6.0/css/iziModal.min.css">
    <title>Profile</title>
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">

</head>

<body>

<div class="container">
    <aside class="sidebar">
        <div class="logo">
            <button class="menu-btn" id="menu-close">
                <i class='bx bx-log-out-circle'></i>
            </button>
            <i class='bx bx-pulse'></i>
            <a href="#">EUPHORIA</a>
        </div>

        <div class="menu">
            <h5>Menu</h5>
            <ul>
                <li>
                    <i class='bx bxs-bolt-circle'></i>
                    <a href="#">Explore</a>
                </li>
                <li>
                    <i class='bx bxs-volume-full'></i>
                    <a href="#">Genres</a>
                </li>
                <li>
                    <i class='bx bxs-album'></i>
                    <a href="#">Albums</a>
                </li>
                <li>
                    <i class='bx bxs-microphone'></i>
                    <a href="#">Artists</a>
                </li>
                <li>
                    <i class='bx bxs-radio'></i>
                    <a href="#">Poddcasts</a>
                </li>
            </ul>
        </div>

        <div class="menu">
            <h5>Library</h5>
            <ul>
                <li>
                    <i class='bx bx-undo'></i>
                    <a href="#">Recent</a>
                </li>
                <li>
                    <i class='bx bxs-photo-album'></i>
                    <a href="#">Albums</a>
                </li>
                <li>
                    <i class='bx bxs-heart'></i>
                    <a href="#">Favourites</a>
                </li>
                <li>
                    <i class='bx bxs-folder'></i>
                    <a href="#">Local</a>
                </li>

            </ul>
        </div>

        <div class="menu">
            <h5>Actions</h5>
            <ul>
                <li>
                    <i class='bx bx-music'></i>
                    <a href="/song/upload">Upload a Song</a>
                </li>
                <li>
                    <i class='bx bx-folder-plus'></i>
                    <a href="/albums/create">Create an album</a>
                </li>
                <li>
                    <i class='bx bxs-caret-right-circle'></i>
                    <a href="#">Best of 2022</a>
                </li>
                <li>
                    <i class='bx bxs-caret-right-circle'></i>
                    <a href="#">Kael Fischer</a>
                </li>

            </ul>
        </div>

        <div class="playing">
            <div class="top">
                <img id="mini-song-image" src="/files/assets/song.png">

                <div class="song-info">
                    <h4 id="mini-song-name">Song name</h4>
                    <p id="mini-artist-name">artist name</p>
                </div>
                <div class="icon">
                    <i id="mini-play-button" class='bx bx-play bx-xs'></i>
                </div>
            </div>
            <div class="bottom">
                <p id="mini-player-status">Paused</p>
            </div>
        </div>


    </aside>

    <main>
        <header>
            <div class="nav-links">
                <button class="menu-btn" id="menu-open">
                    <i class='bx bx-menu'></i>
                </button>
                <a href="/home">Music</a>
                <a href="#">Live</a>
                <a href="#">Podcast</a>
            </div>

            <div class="search">
                <i class='bx bx-search'></i>
                <input type="text" placeholder="Type here to search">
            </div>

        </header>

        <div class="trending">
            <div class="left">
                <h5>Artist Account Info</h5>
                <div class="info">
                    <h2>${artist.stageName}</h2>
                    <h4>@${user.username}</h4>
                    <h5>☑️ Verified Artist</h5>
                    <div class="buttons">
                        <a href="/artist/${user.username}/edit">
                            <button id="changeInfoBtn">Upload Song</button>
                        </a>
                    </div>
                </div>
            </div>
            <img src="/files/img/${user.getProfilePictureUrl()}" alt="Profile Image">
        </div>


        <div class="playlist">
            <div class="song-card-info">
                <div class="card">
                    <img id="song-image" src="/files/assets/song.png">
                    <div class="song-details">
                        <h3 id="song-name">Song Name</h3>
                        <h4 id="artist-name">${artist.stageName}</h4>
                        <p id="release-date">$Release Date</p>
                        <p id="song-genre">Genre</p>
                    </div>
                </div>
            </div>

            <form id="uploadForm" action="/song/upload" method="post" enctype="multipart/form-data">
                <div>
                    <div class="label-wrapper">
                        <label for="title">Song Name:</label>
                    </div>
                    <div class="inputBx">
                        <input type="text" id="title" name="title" placeholder="Title" required>
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="artist-name">Artist Name:</label>
                    </div>
                    <div class="inputBx">
                        <input type="text" id="artist-name" name="artistName" placeholder="Artist Name" required>
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="releaseDate">Release Date:</label>
                    </div>
                    <div class="inputBx">
                        <input type="date" id="releaseDate" name="releaseDate" required>
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="genre">Genre:</label>
                    </div>
                    <div class="inputBx">
                        <select id="genre" name="genre" placeholder="Genre">
                            <option>Genre</option>
                        </select>
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="imageFile">Song Cover:</label>
                    </div>
                    <div class="inputBx">
                        <input type="file" id="imageFile" name="imageFile" accept="image/jpeg, image/png" required>
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="songFile">Song File:</label>
                    </div>
                    <div class="inputBx">
                        <input type="file" id="songFile" name="songFile" accept=".mp3" required>
                    </div>
                </div>
                <div>
                    <input type="hidden" id="userId" name="userId" value="${userId}">
                    <input type="hidden" id="duration" name="duration">
                    <button id="submitBtn" style="display: none;" type="submit">Register</button>
                </div>
            </form>

            <div id="successModal" data-izimodal-transitionin="fadeInDown" data-izimodal-group="alerts"></div>
            <div id="errorModal" data-izimodal-transitionin="fadeInDown" data-izimodal-group="alerts"></div>

        </div>


    </main>

    <div class="right-section">

        <div class="profile">
            <i class='bx bxs-bell'></i>
            <div class="dropdown">
                <i class='bx bxs-cog' id="dropdownMenuButton"></i>
                <div class="dropdown-content" id="dropdownContent">
                    <a href="/profile/${user.username}/edit">Edit Profile</a>
                    <a href="/password/forgot">Change Password</a>
                    <#if user.role == "ARTIST">
                        <a href="/artist/${user.username}">Artist Page</a>
                    </#if>
                    <a href="/logout">Logout</a>
                </div>
            </div>
            <div class="user">
                <a href="/profile/${user.username}" class="user">
                    <div class="left">
                        <img src="/files/img/${user.getProfilePictureUrl()}" alt="Profile Image">
                    </div>
                    <div class="right">
                        <span>${user.username}</span>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
<script src="/static/js/genre.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
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

    // Initialize iziModal for error alert
    $('#errorModal').iziModal({
        icon: 'icon-power_settings_new',
        headerColor: '#BD5B5B',
        backdrop : '',
        width: 600,
        timeout: 15000,
        timeoutProgressbar: true,
        transitionIn: 'fadeInDown',
        transitionOut: 'fadeOutDown',
        pauseOnHover: true
    });

    // Function to update song information based on user input
    function updateSongInfo() {
        // Get input values from the form
        const songName = document.getElementById('title').value;
        const releaseDate = document.getElementById('releaseDate').value;
        const genre = document.getElementById('genre').value;

        // Update the song information card
        document.getElementById('song-name').innerText = songName;
        document.getElementById('release-date').innerText = "Release Date: " + releaseDate;
        document.getElementById('song-genre').innerText = "Genre: " + genre;
    }

    // Event listener for form inputs
    const formInputs = document.querySelectorAll('#uploadForm input, #uploadForm select');
    formInputs.forEach(input => {
        input.addEventListener('input', updateSongInfo);
    });

    // Function to update the song image based on the selected file
    document.getElementById('imageFile').addEventListener('change', function() {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                document.getElementById('song-image').src = e.target.result;
            }
            reader.readAsDataURL(file);
        }
    });


    $('#uploadForm').submit(function (event) {
        event.preventDefault(); // Prevent the form from submitting normally

        // Get the file and calculate its duration
        const file = $('#songFile')[0].files[0];
        if (file) {
            const objectUrl = URL.createObjectURL(file);
            const audio = new Audio(objectUrl);
            audio.onloadedmetadata = function() {
                const duration = Math.floor(audio.duration / 60) + ":" + Math.floor(audio.duration % 60);
                $('#duration').val(duration); // Set the duration to the hidden input field
                submitForm();
            }
        } else {
            submitForm();
        }
    });

    function submitForm() {
        // Perform your form submission with AJAX
        $.ajax({
            url: '/song/upload',
            type: 'post',
            data: new FormData($('#uploadForm')[0]),
            processData: false,
            contentType: false,
            success: function (response) {
                $('#successModal').iziModal('setTitle', 'Success');
                $('#successModal').iziModal('setSubtitle', response);
                $('#successModal').iziModal('open');
            },
            error: function (xhr, status, error) {
                $('#errorModal').iziModal('setTitle', 'Error');
                $('#errorModal').iziModal('setSubtitle', xhr.responseText);
                $('#errorModal').iziModal('open');
                console.error('Error occurred while uploading:', error);
            }
        });
    }
</script>
<script src="/static/js/profile.js"></script>
</body>
</html>