<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/static/css/music/song_upload.css">
    <title>Album Create</title>
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/izimodal/1.6.0/css/iziModal.min.css" rel="stylesheet">


</head>

<body>

<div class="container">
    <aside class="sidebar">
        <div class="logo">
            <button class="menu-btn" id="menu-close">
                <i class='bx bx-log-out-circle'></i>
            </button>
            <i class='bx bx-pulse'></i>
            <a href="/">EUPHORIA</a>
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
                    <a href="/users/favorites/${user.username}">Favourites</a>
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
                    <i class='bx bxs-album'></i>
                    <a href="/albums/${user.username}">Your Albums</a>
                </li>
                <li>
                    <i class='bx bxl-deezer'></i>
                    <a href="/artist/${user.username}">Your Songs</a>
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
                <#if user.role == "ADMIN">
                    <a href="/admin/users">Admin</a>
                <#elseif user.role == "LISTENER" || user.role == "ARTIST">
                    <a href="/users/profile/${user.username}">Profile</a>
                </#if>
            </div>

            <div class="search">
                <i class='bx bx-search'></i>
                <input type="text" id="searchInput" placeholder="Type here to search">
                <div id="searchResults" class="search-results"></div>
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
                            <button id="changeInfoBtn">Create Album</button>
                        </a>
                    </div>
                </div>
            </div>
            <img src="/files/img/${user.getProfilePictureUrl()}" alt="Profile Image">
        </div>


        <div class="playlist">
            <div class="song-card-info">
                <div class="card">
                    <img id="album-image" src="/files/assets/song.png">
                    <div class="song-details">
                        <h3 id="song-name">Album name</h3>
                        <h4 id="artist-name">${artist.stageName}</h4>
                        <p id="release-date">$Release Date</p>
                        <p id="song-genre">Genre</p>
                    </div>
                </div>
            </div>

            <form id="uploadForm" action="/albums/create" method="post" enctype="multipart/form-data">
                <div>
                    <div class="label-wrapper">
                        <label for="title">Album Title:</label>
                    </div>
                    <div class="inputBx">
                        <input type="text" id="title" name="title" placeholder="Title" required>
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
                        <label for="imageFile">Album Cover:</label>
                    </div>
                    <div class="inputBx">
                        <input type="file" id="coverImage" name="coverImage" accept="image/jpeg, image/png" required>
                    </div>
                </div>
                <div>
                    <input type="hidden" id="userId" name="userId" value="${userId}">
                    <button id="submitBtn" style="display: none;" type="submit">Register</button>
                </div>
            </form>
        </div>


    </main>

    <div class="right-section">

        <div class="profile">
            <div class="dropdown" id="notificationDropdown">
                <i class='bx bxs-bell'></i>
                <div class="dropdown-content" id="notificationContent">
                </div>
            </div>
            <div class="dropdown">
                <i class='bx bxs-cog' id="dropdownMenuButton"></i>
                <div class="dropdown-content" id="dropdownContent">
                    <a href="/users/profile/${user.username}/edit">Edit Profile</a>
                    <a href="/password/forgot">Change Password</a>
                    <#if user.role == "ARTIST">
                        <a href="/artist/${user.username}">Artist Page</a>
                    </#if>
                    <a href="/logout">Logout</a>
                </div>
            </div>
            <div class="user">
                <a href="/users/profile/${user.username}" class="user">
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
    <div id="successModal" data-izimodal-transitionin="fadeInDown" data-izimodal-group="alerts">
    </div>
</div>
<script src="https://js.pusher.com/7.0/pusher.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/howler/2.2.3/howler.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/izimodal/1.6.0/js/iziModal.min.js"></script>

<script src="/static/js/genre.js"></script>
<script src="/static/js/pusher.js"></script>
<script src="/static/js/album.js"></script>
<script src="/static/js/search.js"></script>
<script src="/static/js/profile.js"></script>
</body>
</html>