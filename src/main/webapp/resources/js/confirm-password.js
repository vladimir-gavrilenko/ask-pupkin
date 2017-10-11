$(document).ready(function () {
    $('#form').submit(function () {
        var passwordField = $('#password');
        var confirmPasswordField = $('#confirm-password');
        if (passwordField.val() !== confirmPasswordField.val()) {
            confirmPasswordField.setCustomValidity('Password does not match the confirm password.');
            return false;
        }
        return true;
    });
});