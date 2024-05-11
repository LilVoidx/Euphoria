<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/static/css/home/profile.css">
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
            <h5>Playlist</h5>
            <ul>
                <li>
                    <i class='bx bxs-plus-square'></i>
                    <a href="#">Create New</a>
                </li>
                <li>
                    <i class='bx bxs-caret-right-circle'></i>
                    <a href="#">Best of 2023</a>
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
                <img src="assets/current.png">
                <h4>Apple<br>Homepod</h4>
            </div>
            <div class="bottom">
                <i class='bx bx-podcast'></i>
                <p>Playing On Device</p>
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
                    <h5>${user.email}</h5>
                    <div class="buttons">
                        <a href="/artist/${user.username}/edit">
                            <button>Edit Artist info</button>
                        </a>
                        <i class='bx bxs-heart'></i>
                    </div>
                </div>
            </div>
            <img src="/files/img/${user.getProfilePictureUrl()}" alt="Profile Image">
        </div>

        <div class="playlist">
            <div class="genres">
                <div class="header">
                    <h5>Your Albums</h5>
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
                    <h5>Your Songs</h5>
                    <a href="/">See all</a>
                </div>
                <div class="items">
                    <#assign counter = 1>
                    <#list artistSongs as song>
                        <#if counter <= 4>
                            <div class="item">
                                <div class="info">
                                    <p><#if counter <= 4>0</#if>${counter}</p>
                                    <img src="/files/img/${song.songImageInfoUrl}" alt="song image">
                                    <div class="details">
                                        <h5>${song.title}</h5>
                                        <p>${song.artistName}</p>
                                    </div>
                                </div>
                                <div class="actions">
                                    <p>${song.duration}</p>
                                    <div class="icon">
                                        <i class='bx bxs-right-arrow'></i>
                                    </div>
                                    <i class='bx bxs-plus-square'></i>
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
            <i class='bx bxs-bell'></i>
            <div class="dropdown">
                <i class='bx bxs-cog' id="dropdownMenuButton"></i>
                <div class="dropdown-content" id="dropdownContent">
                    <a href="/profile/${user.username}/edit">Edit Profile</a>
                    <a href="/password/forgot">Change Password</a>
                    <#if user.role == "ARTIST">
                        <a href="/artist/${user.username}">Artist Page</a>
                    </#if>
                    <#if user.role == "ARTIST">
                        <a href="/upload-song">Upload Song</a>
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



<script src="/static/js/profile.js"></script>
</body>
</html>