function checkPasswordMatch() {
    var password = $("#pwd").val();
    var confirmPassword = $("pwd2").val();

    if (password == confirmPassword)
        $("#divCheckPasswordMatch").html("Passwords match!");
    else
        $("#divCheckPasswordMatch").html("Passwords not match.");
}

$(document).ready(function () {
   $("#txtConfirmPassword").keyup(checkPasswordMatch);
});
