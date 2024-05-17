<div class="trending">
    <div class="left">
        <h5>Song's Artist Info</h5>
        <div class="info">
            <h2>${songArtist.stageName}</h2>
            <h4>${songArtist.genre}</h4>
            <h5 id="likeCount">${song.likeCount}</h5>
            <div class="buttons">
                <button href="/home">Back Home</button>
                <i class='bx bxs-heart' id="likeButton" data-song-id="${song.songId}"></i>
            </div>
        </div>
    </div>
    <img src="/files/img/${userArtist.getProfilePictureUrl()}" alt="${userArtist.getProfilePictureUrl()}">
</div>