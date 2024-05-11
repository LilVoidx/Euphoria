const menuOpen = document.getElementById('menu-open');
const menuClose = document.getElementById('menu-close');
const sidebar = document.querySelector('.container .sidebar');

menuOpen.addEventListener('click', () => sidebar.style.left = '0');

menuClose.addEventListener('click', () => sidebar.style.left = '-100%');



// This is for the dropdown menu
document.addEventListener("DOMContentLoaded", function(){
    var dropdownContent = document.getElementById("dropdownContent");
    var dropdownMenuButton = document.getElementById("dropdownMenuButton");

    // Initially hide the dropdown content
    dropdownContent.style.display = "none";

    dropdownMenuButton.addEventListener("click", function(event) {
        // Toggle the display of the dropdown content
        if (dropdownContent.style.display === "block") {
            dropdownContent.style.display = "none";
        } else {
            dropdownContent.style.display = "block";
        }
    });

    // Close the dropdown if the user clicks outside of it
    document.addEventListener("click", function(event) {
        var isClickInside = dropdownContent.contains(event.target) || dropdownMenuButton.contains(event.target);
        if (!isClickInside) {
            dropdownContent.style.display = "none";
        }
    });
});

// this is for the preview image when editing the profile
function previewImage(input) {
    var preview = document.getElementById('preview');
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            preview.src = e.target.result;
        }
        reader.readAsDataURL(input.files[0]);
    }
}


// This is for the change info button in the edit profile page
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('changeInfoBtn').addEventListener('click', function(event) {
        // Prevent default button click behavior
        event.preventDefault();

        // Trigger hidden submit button click
        document.getElementById('submitBtn').click();
    });
});


