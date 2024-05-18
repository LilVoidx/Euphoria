<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
    <style>
        a {
            text-decoration: none;
            color: #fff;
        }
        i {
            color: #fff;
            cursor: pointer;
        }
        img {
            object-fit: cover;
            object-position: top;
            border-radius: 8px;
        }
        html,
        body {
            background: linear-gradient(#050505, #18181d);
            height: 100vh;
            color: #fff;
        }
        .navbar {
            background-color: transparent !important; /* Make navbar transparent */
            color: #ccc; /* Set navbar text color */
            padding-right: 30px; /* Add right padding */
        }
        .navbar-nav .nav-link {
            color: #ccc !important; /* Set navbar link color */
        }
        .navbar-toggler {
            border-color: #ccc !important; /* Set toggler color */
        }
        .side-menu {
            height: 100vh; /* Extend sidebar to top and bottom */
            background-color: #343a40; /* Sidebar background color */
            padding-top: 56px; /* Add padding to accommodate navbar */
        }
        .side-menu h5 {
            color: #fff; /* Sidebar text color */
            padding: 20px 0 10px 20px; /* Adjust heading padding */
        }
        .side-menu .nav-link {
            color: #bbb; /* Sidebar links text color */
            padding: 10px 20px; /* Adjust link padding */
            transition: all 0.3s ease; /* Smooth transition */
        }
        .side-menu .nav-link:hover {
            background-color: #454d55; /* Change background color on hover */
            color: #ffffff; /* Change text color on hover */
        }
        .side-menu .active {
            background-color: #454d55; /* Change background color of active link */
            color: #ffffff; /* Change text color of active link */
        }
        .main-content {
            padding: 20px; /* Add padding to main content */
        }
        .table {
            background-color: transparent; /* Make table background transparent */
            color: #fff; /* Set table text color */
        }
        .table th,
        .table td {
            padding: 15px; /* Increase padding */
            border: none; /* Remove table border */
        }
        .table-hover tbody tr:hover {
            background-color: #2a2d34; /* Change hover background color */
            color: #ffffff;
        }
        .pagination a {
            color: #ffffff;
        }
        .pagination .page-item.disabled .page-link,
        .pagination .page-item.active .page-link {
            background-color: #343a40;
            border-color: #454d55;
        }
        .pagination .page-link {
            background-color: #212529;
            border-color: #454d55;
            color: #ffffff;
        }
        .form-control-dark {
            background-color: #343a40;
            color: #ffffff;
            border: 1px solid #454d55;
        }
        .btn-outline-dark {
            color: #ffffff;
            border: 1px solid #ffffff;
        }
        .badge-outline {
            border: 1px solid #ffffff;
            background-color: transparent;
            color: #ffffff;
        }
        .badge-outline.badge-danger,
        .badge-outline.badge-info,
        .badge-outline.badge-success {
            padding: 5px 8px;
        }
        .badge-outline.badge-danger {
            background-color: #dc3545;
        }
        .badge-outline.badge-info {
            background-color: #17a2b8;
        }
        .badge-outline.badge-success {
            background-color: #28a745;
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top"> <!-- Add 'fixed-top' class -->
    <a class="navbar-brand" href="#">Admin Dashboard</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto"> <!-- Align navbar items to right -->
            <li class="nav-item">
                <a class="nav-link" href="#">Link 1</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link 2</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Link 3</a>
            </li>
        </ul>
        <div class="navbar-text">
            <div class="d-flex align-items-center">
                <img src="/files/img/${user.profilePictureUrl}" alt="User Profile Picture" class="img-thumbnail" style="width: 40px; height: 40px;">
                <div class="ml-2">
                    <span>${user.fullName}</span>
                    <span class="badge badge-outline badge-secondary ml-1">${user.role}</span>
                </div>
            </div>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <nav class="col-md-2 d-none d-md-block side-menu"> <!-- Remove bg-dark class from side-menu -->
            <div class="sidebar-sticky">
                <h5 class="text-light">Actions</h5>
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="/home">
                            <i class='bx bxs-home'></i>
                            Home Page
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/users/profile/${user.username}/edit">
                            <i class='bx bxs-edit'></i>
                            Edit Profile
                        </a>
                    </li>
                    <#if user.getRole() == "ARTIST">
                        <li class="nav-item">
                            <a class="nav-link" href="/artist/${user.username}">
                                <i class='bx bxs-microphone'></i>
                                Artist Page
                            </a>
                        </li>
                    <#elseif user.getRole() == "LISTENER">
                        <li class="nav-item">
                            <a class="nav-link" href="/artists/register">
                                <i class='bx bxs-microphone'></i>
                                Become an Artist
                            </a>
                        </li>
                    </#if>
                    <li class="nav-item">
                        <a class="nav-link" href="/password/forgot">
                            <i class='bx bx-refresh'></i>
                            Password Reset
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <main role="main" class="col-md-10 main-content">
            <div class="mt-4">
                <form action="#" method="get" class="form-inline mb-3">
                    <input type="text" name="query" value="${query}" class="form-control form-control-dark mr-2" placeholder="Search users">
                    <button type="submit" class="btn btn-outline-dark">Search</button>
                </form>

                <table class="table table-hover table-striped">
                    <thead>
                    <tr style="border: none;"> <!-- Remove border -->
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
                        <tr>
                            <td>${user.userId}</td>
                            <td><img src="/files/img/${user.profilePictureUrl}" alt="Profile Picture" class="img-thumbnail" style="width: 50px; height: 50px;"></td>
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
                                <a href="/admin/users/${user.username}/edit" class="btn btn-outline-warning btn-sm">Edit</a>
                                <a href="/admin/users/${user.username}/delete" class="btn btn-outline-danger btn-sm">Delete</a>
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

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
