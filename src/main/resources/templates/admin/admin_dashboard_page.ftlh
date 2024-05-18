<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
    <link rel="icon" href="/static/files/assets/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" href="/static/css/admin/admin.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <header class="d-flex w-100">
            <div class="nav-links d-flex flex-grow-1">
                <a href="/admin/songs">Music</a>
                <a href="/admin/users">Users</a>
                <a href="/home">Home</a>
            </div>

            <div class="search d-flex flex-grow-1 justify-content-center">
                <i class='bx bx-search'></i>
                <form action="#" method="get">
                    <input type="text" name="query" value="${query}" placeholder="Type here to search users">
                </form>
            </div>

            <div class="navbar-text d-flex align-items-center">
                <div class="user d-flex">
                    <a href="/users/profile/${user.username}" class="user d-flex">
                        <div class="left d-flex align-items-center">
                            <img src="/files/img/${user.getProfilePictureUrl()}" alt="Profile Image">
                        </div>
                        <div class="right d-flex align-items-center">
                            <span>${user.username}</span>
                        </div>
                    </a>
                </div>
            </div>
        </header>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <aside class="sidebar col-md-2">
            <div class="logo">
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
                        <a href="#">Podcasts</a>
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
                        <i class='bx bxs-edit'></i>
                        <a href="/users/profile/${user.username}/edit">Edit Profile</a>
                    </li>
                    <#if user.role == "ARTIST">
                        <li>
                            <i class='bx bxs-microphone'></i>
                            <a href="/artist/${user.username}">Artist Page</a>
                        </li>
                    <#elseif user.role == "LISTENER" || user.role =="ADMIN">
                        <li>
                            <i class='bx bxs-microphone'></i>
                            <a href="/artists/${user.username}/register">Become an Artist</a>
                        </li>
                    </#if>
                    <li>
                        <i class='bx bx-refresh'></i>
                        <a href="/password/forgot">Password Reset</a>
                    </li>
                </ul>
            </div>
        </aside>

        <main role="main" class="col-md-10 main-content">
            <div class="mt-4">
                <table class="table table-hover table-striped">
                    <thead>
                    <tr style="border: none;">
                        <th style="border: none;">UserID</th>
                        <th style="border: none;">Profile Picture</th>
                        <th style="border: none;">Username</th>
                        <th style="border: none;">Email</th>
                        <th style="border: none;">Full Name</th>
                        <th style="border: none;">Phone Number</th>
                        <th style="border: none;">Registration Date</th>
                        <th style="border: none;">Role</th>
                        <th style="border: none;">Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list usersPage.content as user>
                        <tr id="user-${user.userId}">
                            <td>${user.userId}</td>
                            <td><img src="/files/img/${user.profilePictureUrl}" alt="Profile Picture" style="width: 50px; height: 50px;"></td>
                            <td>${user.username}</td>
                            <td>${user.email}</td>
                            <td>${user.fullName}</td>
                            <td>${user.phoneNumber}</td>
                            <td>${user.registrationDate}</td>
                            <td>
                                <#if user.getRole() == "ADMIN">
                                    <span class="badge badge-outline badge-danger">ADMIN</span>
                                <#elseif user.getRole() == "ARTIST">
                                    <span class="badge badge-outline badge-info">ARTIST</span>
                                <#elseif user.getRole() == "LISTENER">
                                    <span class="badge badge-outline badge-success">LISTENER</span>
                                </#if>
                            </td>
                            <td>
                                <button class="btn btn-outline-danger btn-sm" onclick="deleteUser('${user.username}', ${user.userId})">Delete</button>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>

                <nav class="d-flex justify-content-end">
                    <ul class="pagination">
                        <li class="page-item <#if currentPage == 0>disabled</#if>">
                            <a class="page-link" href="?query=${query}&page=${currentPage - 1}&size=${size}" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </li>
                        <#list 0..usersPage.totalPages - 1 as i>
                            <li class="page-item <#if currentPage == i>active</#if>">
                                <a class="page-link" href="?query=${query}&page=${i}&size=${size}">${i + 1}</a>
                            </li>
                        </#list>
                        <li class="page-item <#if currentPage == usersPage.totalPages - 1>disabled</#if>">
                            <a class="page-link" href="?query=${query}&page=${currentPage + 1}&size=${size}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </main>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    function deleteUser(username, userId) {
        if (confirm('Are you sure you want to delete this user?')) {
            $.ajax({
                url: `/admin/users/`+ username +`/delete`,
                type: 'DELETE',
                success: function(result) {
                    alert('User deleted successfully.');
                    $('#user-' + userId).remove();
                },
                error: function(err) {
                    alert('Failed to delete user.');
                }
            });
        }
    }
</script>
</body>
</html>
