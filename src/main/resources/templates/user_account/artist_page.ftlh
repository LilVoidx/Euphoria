<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/static/css/home/artist_page.css">
    <title>Artist Profile</title>
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/izimodal/1.6.0/css/iziModal.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/css/iziToast.min.css">
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
                            <button>Edit Artist info</button>
                        </a>
                    </div>
                </div>
            </div>
            <img src="/files/img/${user.getProfilePictureUrl()}" alt="Profile Image">
        </div>

        <div class="playlist">
            <div class="music-list">
                <div class="header">
                    <h5>Your Songs</h5>
                    <a href="/">See all</a>
                </div>
                <div class="items">
                    <#assign counter = 1>
                    <#list artistSongs as song>
                        <div class="item song-item" data-song-id="${song.songId}">
                            <div class="info">
                                <p>
                                    <#if counter <= 9>0</#if>${counter}
                                </p>
                                <img src="/files/img/${song.songImageInfoUrl}" alt="${song.title}">
                                <div class="details">
                                    <h5>${song.title}</h5>
                                    <p>${song.artistName}</p>
                                </div>
                            </div>
                            <div class="actions">
                                <p>${song.duration}</p>
                                <div class="icon">
                                    <i class='bx bxs-right-arrow play-song' data-song-id="${song.songId}"></i>
                                </div>
                                <i class='bx bxs-checkbox-minus delete-song' data-song-id="${song.songId}"></i>
                            </div>
                        </div>
                        <#assign counter = counter + 1>
                    </#list>
                </div>
            </div>
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
</div>

<div id="deleteSongModal">
    <p>Are you sure you want to delete this song?</p>
    <button id="confirmDelete">Yes</button>
    <button id="cancelDelete">No</button>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/izimodal/1.6.0/js/iziModal.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/izitoast/1.4.0/js/iziToast.min.js"></script>
<script src="https://js.pusher.com/7.0/pusher.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/howler/2.2.3/howler.min.js"></script>
<script src="/static/js/song_delete.js"></script>
<script src="/static/js/pusher.js"></script>
<script src="/static/js/search.js"></script>
<script src="/static/js/miniPlayer.js"></script>
<script src="/static/js/profile.js"></script>
</body>
</html>