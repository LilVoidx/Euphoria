function showMessage(messageId, message) {
    var element = document.getElementById(messageId);
    if (element) {
        element.innerText = message;
    }
}
