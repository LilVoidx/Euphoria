fetch('/genres')
    .then(response => response.json())
    .then(genres => {
        const select = document.getElementById('genre');
        genres.forEach(genre => {
            const option = document.createElement('option');
            option.value = genre;
            option.textContent = genre;
            select.appendChild(option);
        });
    })
    .catch(error => console.error('Error fetching genres:', error));
