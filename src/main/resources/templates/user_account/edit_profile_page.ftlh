<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <link rel="stylesheet" href="/static/css/home/profile_edit.css">
    <title>Edit Profile</title>
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
                    <i class='bx bxs-microphone' ></i>
                    <a href="/artists/register">Become an Artist</a>
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
                <h5>User Account Info</h5>
                <div class="info">
                    <h2>${user.fullName}</h2>
                    <h4>@${user.username}</h4>
                    <h5>${user.email}</h5>
                    <div class="buttons">
                        <a href="/users/profile/${user.username}/edit">
                            <button id="changeInfoBtn">Change info</button>
                        </a>
                    </div>
                </div>
            </div>
            <img src="/files/img/${user.getProfilePictureUrl()}" id="preview" alt="Profile Image">
        </div>

        <div class="playlist">
            <form method="post" action="/users/profile/${user.username}/edit" enctype="multipart/form-data">
                <div>
                    <div class="label-wrapper">
                        <label for="username">Username:</label>
                    </div>
                    <div class="inputBx">
                        <input type="text" id="username" name="username" value="${user.username}">
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="email">Email:</label>
                    </div>
                    <div class="inputBx">
                        <input type="email" id="email" name="email" value="${user.email}">
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="firstName">First Name:</label>
                    </div>
                    <div class="inputBx">
                        <input type="text" id="firstName" name="firstName" value="${user.firstName}">
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="lastName">Last Name:</label>
                    </div>
                    <div class="inputBx">
                        <input type="text" id="lastName" name="lastName" value="${user.lastName}">
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="phoneNumber">Phone Number:</label>
                    </div>
                    <div class="inputBx">
                        <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}">
                    </div>
                </div>
                <div>
                    <div class="label-wrapper">
                        <label for="profilePicture">Profile Picture:</label>
                    </div>
                    <div class="inputBx">
                        <input type="file" id="profilePicture" name="profilePicture" onchange="previewImage(this)">
                    </div>
                </div>
                <div>
                    <button id="submitBtn" style="display: none;" type="submit">Update</button>
                </div>
            </form>

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
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="/static/js/search.js"></script>
<script src="/static/js/profile.js"></script>
</body>
</html>