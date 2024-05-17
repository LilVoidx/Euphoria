// Define a function to handle liking/unliking a song
function likeSong(songId) {
    // Send request to like/unlike the song
    $.ajax({
        url: "/song/" + songId + "/favorite",
        type: "POST",
        dataType: "json",
        success: function(response) {
            // Update like count on the front end
            document.getElementById('likeCount').textContent = response.likeCount;
        },
        error: function(xhr, status, error) {
            console.error('Error liking the song:', error);
        }
    });
}

// Add event listener to the heart icon
document.getElementById('likeButton').addEventListener('click', function() {
    // Get the song ID from the data attribute of the heart icon
    const songId = this.getAttribute('data-song-id');

    // Call the likeSong function with the songId
    likeSong(songId);
});
