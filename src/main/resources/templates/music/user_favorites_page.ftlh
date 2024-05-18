<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/static/css/music/song.css">
    <title>Favourites Playlist</title>
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
                    <i class='bx bxs-home'></i>
                    <a href="/home">Home Page</a>
                </li>
                <li>
                    <i class='bx bxs-edit' ></i>
                    <a href="/users/profile/${user.username}/edit">Edit Profile</a>
                </li>
                <li>
                    <#if user.role == "ARTIST">
                        <i class='bx bxs-microphone' ></i>
                        <a href="/artist/${user.username}">Artist Page</a>
                    <#elseif user.role == "LISTENER" || user.role =="ADMIN">
                        <i class='bx bxs-microphone' ></i>
                        <a href="/artists/${user.username}/register">Become an Artist</a>
                    </#if>

                </li>
                <li>
                    <i class='bx bx-refresh' ></i>
                    <a href="/password/forgot">Password Reset</a>
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
                <input type="text" id="searchInput" placeholder="Type here to search">
                <div id="searchResults" class="search-results"></div>
            </div>

        </header>

        <div class="trending">
            <div class="left">
                <h5>Favourite Songs Playlist</h5>
                <div class="info">
                    <h2>Favourite Songs</h2>
                    <h4>@${user.getUsername()}</h4>
                    <h5>${user.fullName}</h5>
                    <div class="buttons">
                        <a href="/home">
                            <button>Back Home</button>
                        </a>
                    </div>
                </div>
            </div>
            <img src="/files/assets/fav.jpeg" alt="fav">
        </div>


        <div class="playlist">
            <div class="music-list">
                <div class="header">
                    <h5>Your liked songs</h5>
                    <a href="#">See all</a>
                </div>

                <div class="items">
                    <#assign counter = 1>
                    <#list songs as song>
                        <div class="item song-item" data-song-id="${song.songId}">
                            <div class="info">
                                <p><#if counter <= 9>0</#if>${counter}</p>
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
                                <i class='bx bxs-plus-square'></i>
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
            <i class='bx bxs-bell'></i>
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

        <div class="music-player">
            <div class="top-section">
                <div class="header">
                    <h5>Player</h5>
                    <i class='bx bxs-playlist'></i>
                </div>
                <div class="song-info">
                    <img id="song-image" src="/files/assets/song.png">
                    <div class="description">
                        <h3 id="song-name">Title</h3>
                        <h5 id="artist-name">artist</h5>
                        <p id="album-name">album</p>
                    </div>
                    <div class="progress">
                        <p class="current-time">00:00</p>
                        <div class="progress-bar">
                            <div class="active-line"></div>
                            <div class="progress-ball"></div>
                        </div>
                        <p class="duration">00:00</p>
                    </div>
                    <div class="sound-box">
                        <i class='bx bx-minus volume-down'></i>
                        <input type="range" min="0" max="100" value="100" class="volume-slider">
                        <i class='bx bx-plus volume-up'></i>
                    </div>
                </div>
            </div>

            <div class="player-actions">
                <div class="buttons">
                    <i class='bx bx-heart' id="playLikeButton" data-song-id=""></i>
                    <i class='bx bx-first-page'></i>
                    <i class='play-button bx bxs-right-arrow'></i>
                    <i class='bx bx-last-page'></i>
                    <i class='bx bx-volume-full'></i>
                </div>
                <div class="lyrics">
                    <i class='bx bx-chevron-up'></i>
                    <h5>LYRICS</h5>
                </div>
            </div>
        </div>


    </div>

</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="/static/js/favorite.js"></script>
<script src="/static/js/search.js"></script>
<script src="/static/js/player.js"></script>
<script src="/static/js/miniPlayer.js"></script>
<script src="/static/js/profile.js"></script>
</body>

</html>