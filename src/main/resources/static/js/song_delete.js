$(document).ready(function() {
    // Delete song when delete button is clicked
    $('.delete-song').on('click', function() {
        var songId = $(this).data('song-id');
        console.log('Delete button clicked for song ID:', songId);
        if (confirm('Are you sure you want to delete this song?')) {
            $.ajax({
                url: '/song/' + songId + '/delete',
                type: 'DELETE',
                success: function(response) {
                    console.log('Song deleted successfully');
                    iziToast.success({
                        title: 'Success',
                        message: 'Song deleted successfully!',
                        position: 'topRight'
                    });
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
                }
            });
        }
    });
});
