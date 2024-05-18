function uploadProfilePicture() {
    var formData = new FormData($('#profilePictureForm')[0]);
    var submitButton = $('input[type="button"]');
    // Disable the submit button
    submitButton.prop('disabled', true);

    $.ajax({
        url: '/signUp/uploadProfilePicture',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        dataType: 'json',
        success: function(response) {
            if (response.status === 'success') {
                window.location.href = response.redirectUrl;
            } else {
                alert('Error uploading profile picture: ' + response.message);
                // Re-enable the button on error
                submitButton.prop('disabled', false);
            }
        },
        error: function(xhr, status, error) {
            alert('Error uploading profile picture: ' + xhr.responseText);
            submitButton.prop('disabled', false);
        }
    });
}