// Initialize iziModal for success alert
$('#successModal').iziModal({
    icon: 'icon-check',
    headerColor: '#00af66',
    backdrop : '',
    width: 600,
    timeout: 15000,
    timeoutProgressbar: true,
    transitionIn: 'fadeInDown',
    transitionOut: 'fadeOutDown',
    pauseOnHover: true,
});

// Initialize iziModal for error alert
$('#errorModal').iziModal({
    icon: 'icon-power_settings_new',
    headerColor: '#BD5B5B',
    backdrop : '',
    width: 600,
    timeout: 15000,
    timeoutProgressbar: true,
    transitionIn: 'fadeInDown',
    transitionOut: 'fadeOutDown',
    pauseOnHover: true
});

// Function to update song information based on user input
function updateSongInfo() {
    // Get input values from the form
    const songName = document.getElementById('title').value;
    const releaseDate = document.getElementById('releaseDate').value;
    const genre = document.getElementById('genre').value;

    // Update the song information card
    document.getElementById('song-name').innerText = songName;
    document.getElementById('release-date').innerText = "Release Date: " + releaseDate;
    document.getElementById('song-genre').innerText = "Genre: " + genre;
}

// Event listener for form inputs
const formInputs = document.querySelectorAll('#uploadForm input, #uploadForm select');
formInputs.forEach(input => {
    input.addEventListener('input', updateSongInfo);
});

// Function to update the song image based on the selected file
document.getElementById('imageFile').addEventListener('change', function() {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('song-image').src = e.target.result;
        }
        reader.readAsDataURL(file);
    }
});


$('#uploadForm').submit(function (event) {
    event.preventDefault(); // Prevent the form from submitting normally

    // Get the file and calculate its duration
    const file = $('#songFile')[0].files[0];
    if (file) {
        const objectUrl = URL.createObjectURL(file);
        const audio = new Audio(objectUrl);
        audio.onloadedmetadata = function() {
            const duration = Math.floor(audio.duration / 60) + ":" + Math.floor(audio.duration % 60);
            $('#duration').val(duration); // Set the duration to the hidden input field
            submitForm();
        }
    } else {
        submitForm();
    }
});

function submitForm() {
    // Posting form submission with AJAX
    $.ajax({
        url: '/song/upload',
        type: 'post',
        data: new FormData($('#uploadForm')[0]),
        processData: false,
        contentType: false,
        success: function (response) {
            $('#successModal').iziModal('setTitle', 'Success');
            $('#successModal').iziModal('setSubtitle', response);
            $('#successModal').iziModal('open');
        },
        error: function (xhr, status, error) {
            $('#errorModal').iziModal('setTitle', 'Error');
            $('#errorModal').iziModal('setSubtitle', xhr.responseText);
            $('#errorModal').iziModal('open');
            console.error('Error occurred while uploading:', error);
        }
    });
}