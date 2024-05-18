function likeSong(songId) {
    // Send request to like/unlike the song
    $.ajax({
        url: "/song/" + songId + "/favorite",
        type: "POST",
        dataType: "json",
        success: function(response) {
            // Extract the song and isFavorite from the response
            const song = response.song;
            const isFavorite = response.isFavorite;

            // Get the current text (title and like count) of the likeCount element
            const likeCountElement = document.getElementById('likeCount');
            const currentText = likeCountElement.textContent;

            // Extract the song title from the current text
            const songTitle = currentText.split(' - ')[0];

            // Update the like count on the front end while preserving the song title
            likeCountElement.textContent = `${songTitle} - ${song.likeCount} Million Favorites`;

            // Update the appearance of the like button based on isFavorite status
            const likeButton = document.getElementById('likeButton');
            likeButton.classList.toggle('highlighted');

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
