$(document).ready(function () {
    document.getElementById("unslashed-eye").hidden = false;
    document.getElementById("slashed-eye").hidden = true;

    $("#password2").tooltip({
        trigger: "mouseenter",
        title:
            "Deve essere formata almeno da 8 caratteri, di cui almeno 1 numero, 1 lettera maiuscola ed 1 lettera minuscola",
    });

    $("#username2").tooltip({
        trigger: "mouseenter",
        title:
            "Inserire un nome utente. Sono ammessi solo i seguenti caratteri speciali: /+-_@$£€*",
    });

    $("#accessoGuest").tooltip({
        trigger: "mouseenter",
        placement: "bottom",
        title: "Attenzione: eseguendo l'accesso come ospite potrai solo visualizzare le ripetizioni, non prenotarle.",
    });

    $("#password2").mouseenter(function () {
        var that = $(this);
        that.tooltip("show");
        setTimeout(function () {
            that.tooltip("hide");
        }, 3000);
    });

    $("#password2").mouseleave(function () {
        $(this).tooltip("hide");
    });

    $("#username2").mouseenter(function () {
        var that = $(this);
        that.tooltip("show");
        setTimeout(function () {
            that.tooltip("hide");
        }, 3000);
    });

    $("#username2").mouseleave(function () {
        $(this).tooltip("hide");
    });

    $("#accessoGuest").mouseenter(function () {
        var that = $(this);
        that.tooltip("show");
        setTimeout(function () {
            that.tooltip("hide");
        }, 5000);
    });

    $("#accessoGuest").mouseleave(function () {
        $(this).tooltip("hide");
    });

    $("#show_hide_password span img").on("click", function (event) {
        event.preventDefault();
        if ($("#show_hide_password input").attr("type") == "text") {
            $("#show_hide_password input").attr("type", "password");
            document.getElementById("unslashed-eye").hidden = false;
            document.getElementById("slashed-eye").hidden = true;
        } else if ($("#show_hide_password input").attr("type") == "password") {
            $("#show_hide_password input").attr("type", "text");
            document.getElementById("slashed-eye").hidden = false;
            document.getElementById("unslashed-eye").hidden = true;
        }
    });
    document.getElementById("nome").onblur = function () {
        var numbers = /[0-9]/g;
        var specials = /[^a-zA-Z]+/g;
        var nome = document.getElementById("nome");

        if (
            nome.value.match(numbers) ||
            nome.value.match(specials) ||
            nome.value.match(" ") ||
            nome.value.length == 0
        ) {
            nome.classList.remove("is-valid");
            nome.classList.add("is-invalid");
        } else {
            nome.classList.remove("is-invalid");
            nome.classList.add("is-valid");
        }
    };
    document.getElementById("cognome").onblur = function () {
        var numbers = /[0-9]/g;
        var specials = /[^a-zA-Z]+/g;
        var cognome = document.getElementById("cognome");

        if (
            cognome.value.match(numbers) ||
            cognome.value.match(specials) ||
            cognome.value.match(" ") ||
            cognome.value.length == 0
        ) {
            cognome.classList.remove("is-valid");
            cognome.classList.add("is-invalid");
        } else {
            cognome.classList.remove("is-invalid");
            cognome.classList.add("is-valid");
        }
    };
    document.getElementById("username2").onblur = function () {
        var specials = /[^a-zA-Z0-9+-\\/_@$£€*]+/g;
        var username = document.getElementById("username2");

        if (
            username.value.match(specials) ||
            username.value.match(" ") ||
            username.value.length == 0
        ) {
            username.classList.remove("is-valid");
            username.classList.add("is-invalid");
        } else {
            username.classList.remove("is-invalid");
            username.classList.add("is-valid");
        }
    };

    document.getElementById("email").onblur = function () {
        var specials = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        var email = document.getElementById("email");

        if (
            !email.value.match(specials) ||
            email.value.match(" ") ||
            email.value.length == 0
        ) {
            email.classList.remove("is-valid");
            email.classList.add("is-invalid");
        } else {
            email.classList.remove("is-invalid");
            email.classList.add("is-valid");
        }
    };
});

function passwordValidation() {
    var myInput2 = document.getElementById("password2");
    var letter = document.getElementById("minuscola");
    var capital = document.getElementById("maiuscola");
    var number = document.getElementById("numero");
    var length = document.getElementById("lunghezza");

    var lowerCaseLetters = /[a-z]/g;
    var upperCaseLetters = /[A-Z]/g;
    var numbers = /[0-9]/g;

    if (
        myInput2.value.match(lowerCaseLetters) &&
        myInput2.value.match(upperCaseLetters) &&
        myInput2.value.match(numbers) &&
        myInput2.value.length >= 8
    ) {
        myInput2.classList.remove("is-invalid");
        myInput2.classList.add("is-valid");
    } else {
        myInput2.classList.remove("is-valid");
        myInput2.classList.add("is-invalid");

        if (myInput2.value.match(lowerCaseLetters)) {
            letter.classList.remove("invalid2");
            letter.classList.add("valid2");
        } else {
            letter.classList.remove("valid2");
            letter.classList.add("invalid2");
        }

        if (myInput2.value.match(upperCaseLetters)) {
            capital.classList.remove("invalid2");
            capital.classList.add("valid2");
        } else {
            capital.classList.remove("valid2");
            capital.classList.add("invalid2");
        }

        if (myInput2.value.match(numbers)) {
            number.classList.remove("invalid2");
            number.classList.add("valid2");
        } else {
            number.classList.remove("valid2");
            number.classList.add("invalid2");
        }

        if (myInput2.value.length >= 8) {
            length.classList.remove("invalid");
            length.classList.add("valid");
        } else {
            length.classList.remove("valid");
            length.classList.add("invalid");
        }
    }
}

function doLogIn(form) {
    const username = form.username.value;
    const password = form.password.value;

    let user;

    jQuery.ajaxSetup({async: false});
    $.get("Login", {action: 'login', username: username, password: password}, function (data) {
            user = data;
        }
    );
    jQuery.ajaxSetup({async: true});

    console.log(user);
    if (user === 'Utente non riconosciuto') {
        alert("Utente non riconosciuto!");
        return false;
    } else {
        return true;
    }
}

function doLogInGuest() {
    let user;

    jQuery.ajaxSetup({async: false});
    $.get("Login", {action: 'loginGuest'}, function (data) {
            user = data;
        }
    );
    jQuery.ajaxSetup({async: true});

    console.log(user);
    return true;
}

function doRegister(form) {
    const nome = form.nome.value;
    const cognome = form.cognome.value;
    const username = form.username2.value;
    const password = form.password2.value;

    let user;

    jQuery.ajaxSetup({async: false});
    $.get("Login", {
            action: 'register',
            nome: nome,
            cognome: cognome,
            username: username,
            password: password
        }, function (data) {
            user = data
        }
    );
    jQuery.ajaxSetup({async: true});

    if (user === "Impossibile creare l'utente") {
        alert("Username già in uso");
        return false;
    } else {
        console.log("Bienvenido");
        return true;
    }
}

function reload() {
    window.location.reload();
}
