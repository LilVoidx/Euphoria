$(document).ready(function() {
    // Load notifications from localStorage
    if (localStorage.getItem('notifications')) {
        const notifications = JSON.parse(localStorage.getItem('notifications'));
        notifications.forEach(notification => {
            $('#notificationContent').append(notification);
        });
        if (notifications.length > 0) {
            $('#notificationDropdown i').removeClass('bxs-bell').addClass('bxs-bell-ring');
        }
    }

    var pusher = new Pusher('9cbfe7f81e5fcfe7fceb', {
        cluster: 'eu'
    });

    var channel = pusher.subscribe('music-channel');
    channel.bind('new-song', function(data) {
        // Handle the new song notification
        var notification = '<a href="/song/' + data.songId + '" class="notification">' + data.message + ": " + data.songTitle + '</a>';
        $('#notificationContent').append(notification);
        // Change the bell icon class
        $('#notificationDropdown i').removeClass('bxs-bell').addClass('bxs-bell-ring');

        // Play notification sound
        var sound = new Howl({
            src: ['/static/files/assets/mixkit-correct-answer-tone-2870.wav']
        });
        sound.play();

        // Add notification to localStorage
        const notifications = JSON.parse(localStorage.getItem('notifications')) || [];
        notifications.push(notification);
        localStorage.setItem('notifications', JSON.stringify(notifications));
    });

    // Handle clicking on a notification
    $(document).on('click', '.notification', function() {
        var notificationHTML = $(this).prop('outerHTML');
        $(this).remove();

        // Remove notification from localStorage
        const notifications = JSON.parse(localStorage.getItem('notifications')) || [];
        const index = notifications.indexOf(notificationHTML);
        if (index !== -1) {
            notifications.splice(index, 1);
            localStorage.setItem('notifications', JSON.stringify(notifications));
        }

        // Change the bell icon class if there are no more notifications
        if ($('#notificationContent').children().length === 0) {
            $('#notificationDropdown i').removeClass('bxs-bell-ring').addClass('bxs-bell');
        }
    });
});