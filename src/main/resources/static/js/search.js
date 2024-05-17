// Function to fetch search results and update dropdown list
function searchSongs(query) {
    $.ajax({
        url: "/search",
        type: "GET",
        data: { query: query },
        dataType: "json",
        success: function(songs) {
            displaySearchResults(songs);
        },
        error: function(xhr, status, error) {
            console.error('Error fetching search results:', error);
        }
    });
}

// Function to display search results in the dropdown list
function displaySearchResults(songs) {
    const searchResults = document.getElementById('searchResults');
    searchResults.innerHTML = '';

    if (songs.length === 0) {
        searchResults.innerHTML = '<p>No results found</p>';
    } else {
        const ul = document.createElement('ul');
        songs.forEach(song => {
            const li = document.createElement('li');

            const img = document.createElement('img');
            img.src = "/files/img/" + song.songImageInfoUrl;
            img.alt = song.title;
            li.appendChild(img);

            const title = document.createElement('span');
            title.textContent = song.title;
            li.appendChild(title);

            li.addEventListener('mouseenter', function() {
                li.classList.add('hover');
            });

            li.addEventListener('mouseleave', function() {
                li.classList.remove('hover');
            });

            li.addEventListener('click', function() {
                window.location.href = "/song/" + song.songId;
            });

            ul.appendChild(li);
        });
        searchResults.appendChild(ul);
    }
    searchResults.style.display = 'block'; // Display the search results
}

// Event listener for input change in the search bar
const searchInput = document.getElementById('searchInput');
searchInput.addEventListener('input', function() {
    const query = this.value.trim();
    if (query.length > 0) {
        searchSongs(query);
    } else {
        // Clear the dropdown list if the search input is empty
        const searchResults = document.getElementById('searchResults');
        searchResults.innerHTML = '';
        searchResults.style.display = 'none';
    }
});
