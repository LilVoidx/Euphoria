<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/static/css/home/home.css">
    <title>Home</title>
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
                <a href="#">Music</a>
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
                <h5>Trending New Song</h5>
                <div class="info trending-song-info" data-song-id="${trendingSong.songId}">
                    <h2>${trendingSong.title}</h2>
                    <h4>${trendingSong.artistName}</h4>
                    <h5 id="likeCount">${trendingSong.likeCount} Million Favorites</h5>
                    <div class="buttons">
                        <button class="play-song">Listen Now</button>
                        <i class='bx bxs-heart' id="likeButton" data-song-id="${trendingSong.songId}"></i>
                    </div>
                </div>
            </div>
            <img src="/files/img/${trendingSong.songImageInfoUrl}" alt="${trendingSong.songImageInfoUrl}">
        </div>

        <div class="playlist">
            <div class="genres">
                <div class="header">
                    <h5>Genres</h5>
                    <a href="#">See all</a>
                </div>

                <div class="items">
                    <div class="item">
                        <p>Electro<br>Pop</p>
                    </div>
                    <div class="item">
                        <p>Dance<br>Beeat</p>
                    </div>
                    <div class="item">
                        <p>Clubhouse<br>Remix</p>
                    </div>
                    <div class="item">
                        <p>Hip Hop<br>Rap</p>
                    </div>
                    <div class="item">
                        <p>Alternative<br>Indie</p>
                    </div>
                    <div class="item">
                        <p>Classical<br>Period</p>
                    </div>
                </div>

            </div>

            <div class="music-list">
                <div class="header">
                    <h5>Top Songs</h5>
                    <a href="#">See all</a>
                </div>

                <div class="items">
                    <#assign counter = 1>
                    <#list allSongs as song>
                        <#if counter <= 4>
                            <div class="item song-item" data-song-id="${song.songId}">
                                <div class="info">
                                    <p><#if counter <= 4>0</#if>${counter}</p>
                                    <img src="/files/img/${song.songImageInfoUrl}" alt="${song.title}">
                                    <div class="details">
                                        <h5>${song.title}</h5>
                                        <p>${song.artistName}</p>
                                    </div>
                                </div>
                                <div class="actions">
                                    <p>${song.duration}</p>
                                    <div class="icon">
                                        <i class='bx bxs-right-arrow play-song'></i>
                                    </div>
                                    <i class='bx bxs-plus-square' id="likeButton" data-song-id="${song.songId}"></i>
                                </div>
                            </div>
                            <#assign counter = counter + 1>
                        </#if>
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
<script src="https://js.pusher.com/7.0/pusher.min.js"></script>
<script src="https://js.pusher.com/beams/1.0/push-notifications-cdn.js"></script>
<script>
    var pusher = new Pusher('9cbfe7f81e5fcfe7fceb', {
        cluster: 'eu'
    });

    var channel = pusher.subscribe('music-channel');
    channel.bind('new-song', function(data) {
        // Handle the new song notification
        var notification = '<a href="#" class="notification">' + data.message + ": " + data.songTitle + '</a>';
        $('#notificationContent').append(notification);
        // Change the bell icon class
        $('#notificationDropdown i').removeClass('bxs-bell').addClass('bxs-bell-ring');
    });

    // Handle clicking on a notification
    $(document).on('click', '.notification', function() {
        $(this).remove();
        // Change the bell icon class if there are no more notifications
        if ($('#notificationContent').children().length === 0) {
            $('#notificationDropdown i').removeClass('bxs-bell-ring').addClass('bxs-bell');
        }
    });
</script>


<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="/static/js/favorite.js"></script>
<script src="/static/js/search.js"></script>
<script src="/static/js/player.js"></script>
<script src="/static/js/miniPlayer.js"></script>
<script src="/static/js/profile.js"></script>
</body>

</html>