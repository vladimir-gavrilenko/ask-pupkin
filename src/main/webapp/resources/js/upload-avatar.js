$(document).ready(function () {
    var uploadButton = $('#upload-avatar-button');
    var progressBar = $('#avatar-upload-progress').find('.progress-bar');
    var avatarImg = $('#avatar-img');
    var avatarError = $('#upload-avatar-error');
    var avatarSuccess = $('#upload-avatar-success');

    $('#avatar-file-upload-input').fileupload({
        url: window.avatarUploadUrl,
        dataType: "json",
        send: function (e, data) {
            progressBar.css('width', '0');
            progressBar.removeClass('progress-bar-danger').addClass('progress-bar-info');
            progressBar.parent().show();
            avatarError.hide();
            avatarSuccess.hide();
            uploadButton.addClass('disabled');
        },
        done: function (e, data) {
            if (data.result.status === 'ok') {
                avatarImg.attr('src', data.result.url);
                // progressBar.parent().hide();
                avatarSuccess.show();
            } else {
                progressBar.removeClass('progress-bar-info').addClass('progress-bar-danger');
                avatarError.show();
            }
        },
        fail: function (e, data) {
            progressBar.removeClass('progress-bar-info').addClass('progress-bar-danger');
            avatarError.show();
        },
        always: function (e, data) {
            uploadButton.removeClass('disabled');
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            progressBar.css('width', progress + '%');
        }
    }).prop('disabled', !$.support.fileInput).parent().addClass($.support.fileInput ? undefined : 'disabled');
});
