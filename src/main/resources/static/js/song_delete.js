$(document).ready(function() {
    // Initialize modal
    $('#deleteSongModal').iziModal({
        headerColor: '#f00',
        overlayColor: 'rgba(0, 0, 0, 0.5)',
        zindex: 1050
    });

    // Open modal when delete button is clicked
    $('.delete-song').on('click', function() {
        var songId = $(this).data('song-id');
        console.log('Delete button clicked for song ID:', songId);
        $('#deleteSongModal').iziModal('open');
        // Store songId in confirm button
        $('#confirmDelete').data('song-id', songId);
    });

    // Delete song when confirm button in modal is clicked
    $('#confirmDelete').on('click', function() {
        var songId = $(this).data('song-id');
        console.log('Confirm delete for song ID:', songId);
        $.ajax({
            url: '/song/' + songId + '/delete',
            type: 'POST',
            success: function(response) {
                console.log('Song deleted successfully');
                iziToast.success({
                    title: 'Success',
                    message: 'Song deleted successfully!',
                    position: 'topRight'
                });
                $('#deleteSongModal').iziModal('close');
                // Remove the song item from the DOM
                $('#song-' + songId).remove();
                location.reload();
            },
            error: function(xhr, status, error) {
                console.log('Error deleting song:', error);
                iziToast.error({
                    title: 'Error',
                    message: 'Failed to delete song',
                    position: 'topRight'
                });
                $('#deleteSongModal').iziModal('close');
            }
        });
    });

    // Close modal when cancel button in modal is clicked
    $('#cancelDelete').on('click', function() {
        console.log('Cancel delete');
        $('#deleteSongModal').iziModal('close');
    });
});