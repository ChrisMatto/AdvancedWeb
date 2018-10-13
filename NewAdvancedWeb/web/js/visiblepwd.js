function show() {
    var p = document.getElementById('pwd');
    p.setAttribute('type', 'text');
}

function hide() {
    var p = document.getElementById('pwd');
    p.setAttribute('type', 'password');
}

var pwShown = 0;

document.getElementById("eye").addEventListener("click", function () {
    if (pwShown == 0) {
        document.getElementById("eye").value = "Hide password";
        pwShown = 1;
        show();
    
    } else {
        document.getElementById("eye").value = "Show password";
        pwShown = 0;
        hide();
    }
}, false);