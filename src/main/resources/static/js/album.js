// create

$(document).ready(function () {
    // Function to update album information based on user input
    function updateAlbumInfo() {
        const albumTitle = $('#title').val();
        const releaseDate = $('#releaseDate').val();
        const genre = $('#genre').val();

        $('#song-name').text(albumTitle || 'Album name');
        $('#release-date').text(releaseDate ? 'Release Date: ' + releaseDate : 'Release Date');
        $('#song-genre').text(genre || 'Genre');
    }

    // Event listeners for form inputs
    $('#uploadForm input, #uploadForm select').on('input change', updateAlbumInfo);

    // Function to update the album cover image based on the selected file
    $('#coverImage').on('change', function() {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(e) {
                $('#album-image').attr('src', e.target.result);
            }
            reader.readAsDataURL(file);
        }
    });

    // AJAX form submission
    $('#uploadForm').on('submit', function (event) {
        event.preventDefault();

        $.ajax({
            url: '/albums/create',
            type: 'post',
            data: new FormData(this),
            processData: false,
            contentType: false,
            success: function (response) {
                $('#successModal').iziModal('setTitle', 'Album Created!');
                $('#successModal').iziModal('setSubtitle', 'Album Created successfully!');
                $('#successModal').iziModal('open');
            },
            error: function (xhr, status, error) {
                alert('There was a problem with your request.');
                console.error('Error:', error);
            }
        });
    });
});



//delete
$(document).ready(function() {
    // Open modal when delete button is clicked
    $('.delete-album').on('click', function() {
        var albumId = $(this).data('album-id');
        console.log('Delete button clicked for album ID:', albumId);
        var confirmDelete = confirm("Are you sure you want to delete this album?");
        if (confirmDelete) {
            $.ajax({
                url: '/albums/' + albumId + '/delete',
                type: 'DELETE',
                success: function(response) {
                    console.log('Album deleted successfully');
                    iziToast.success({
                        title: 'Success',
                        message: 'Album deleted successfully!',
                        position: 'topRight'
                    });
                    // Remove the album item from the DOM
                    $('#album-' + albumId).remove();
                    location.reload();
                },
                error: function(xhr, status, error) {
                    console.log('Error deleting album:', error);
                    iziToast.error({
                        title: 'Error',
                        message: 'Failed to delete album',
                        position: 'topRight'
                    });
                }
            });
        }
    });
});

//add song

// Initialize iziModal for success alert
$('#successModal').iziModal({
    icon: 'icon-check',
    headerColor: '#00af66',
    backdrop: '',
    width: 600,
    timeout: 15000,
    timeoutProgressbar: true,
    transitionIn: 'fadeInDown',
    transitionOut: 'fadeOutDown',
    pauseOnHover: true,
    onClosed: function() {
        location.reload();
    }
});

$(document).ready(function() {
    // Handle adding a song
    $('.add-song').click(function() {
        var songId = $(this).data('song-id');
        var albumId = $(this).data('album-id');
        var username = $(this).data('user-id');
        var songItem = $(this).closest('.item');
        $.ajax({
            type: 'POST',
            url: '/albums/' + username + '/album/' + albumId + '/addSong',
            data: { songId: songId },
            success: function() {
                $('#successModal').iziModal('setTitle', 'Song Added');
                $('#successModal').iziModal('setSubtitle', 'Song has been added to the album successfully!');
                $('#successModal').iziModal('open');
                songItem.remove();
            },
            error: function() {
                alert('Error adding song to the album!');
            }
        });
    });

    // Handle removing a song
    $('.remove-song').click(function() {
        var songId = $(this).data('song-id');
        var albumId = $(this).data('album-id');
        var username = $(this).data('user-id');
        var songItem = $(this).closest('.item');

        $.ajax({
            type: 'POST',
            url: '/albums/' + username + '/album/' + albumId + '/removeSong',
            data: { songId: songId },
            success: function() {
                // Show a success modal
                $('#successModal').iziModal('setTitle', 'Song Removed');
                $('#successModal').iziModal('setSubtitle', 'Song has been removed from the album successfully!');
                $('#successModal').iziModal('open');

                // Remove the song item from the list
                songItem.remove();
            },
            error: function() {
                alert('Error removing song from the album!');
            }
        });
    });
});