new Vue({
    el: '#app',
    data: {
        sessione: "Sessione inesistente",
        utente: {
            username: '',
            password: '',
            nome: '',
            cognome: '',
            ruolo: ''
        },
        // Dati presi dal DB
        docenti: [],
        corsi: [],
        ripetizioni: [],
        corsidocenti: [],
        prenotazioni: [],
        // V-if
        paginaServizi: true,
        showRipetizioni: true,
        showPrenotazioni: false,
        showCorsiDocenti: false,
        // Link alle Servlet
        sLogin: 'Login',
        sManageDB: 'ManageDBServlet',
        sRipetizioni: 'Ripetizioni',
        sPrenotazioni: 'Prenotazioni',
        sShowRipetizioni: 'Ripetizioni',
        // Form assegna Docente a Corso
        titoloCorso: '',
        idDocente: '',
        idRipetizione: ''
    },
    methods: {
        getSession: function () {
            var self = this;
            $.get(self.sLogin, {action: 'getSession'}, function (data) {
                self.utente.username = data.username;
                self.utente.password = data.password;
                self.utente.nome = data.nome;
                self.utente.cognome = data.cognome;
                self.utente.ruolo = data.ruolo;
                self.sessione = data.sessione;
                $("#checkprocess").text(data);
                $("#infoUtente").text(data);
            });
        },
        logOut: function () {
            var self = this;
            console.log("Inizio la logout" + self.utente.username);
            let dati = "init dati";

            $.post(self.sLogin, {action: 'logout', session: self.sessione}, function (data) {
                dati = data;
                console.log(self.utente.username);
                self.utente = {};
                console.log(self.utente.username + " data: " + data);
            }).done(() => console.log(dati))
                .catch(err => console.log("ERRORE: " + err.error))
                .fail(() => console.log("error " + dati))

            window.location.href = 'login.html';
            console.log("Finisco la logout" + self.utente.username);
        },
        showPrenotazioniFunc: function () {
            console.log("Inizio PRENOTAZIONIFUNC");
            var self = this;

            if (this.showPrenotazioni === false)
                self.showPrenotazioni = true;
            self.showRipetizioni = false;
            self.showCorsiDocenti = false;

            if (self.sessione !== 0) self.updatePrenotazioniFunc();

            console.log("Fine PRENOTAZIONIFUNC");
        },
        showRipetizioniFunc: function () {
            console.log("Inizio RIPETIZIONIFUNC");
            var self = this;

            if (this.showRipetizioni === false)
                self.showRipetizioni = true;
            self.showPrenotazioni = false;
            self.showCorsiDocenti = false;

            $("#infoUtente").text(self.utente.username + " con ruolo " + self.utente.ruolo);

            if (self.sessione !== 0) self.updateRipetizioniFunc();

            console.log("Finisco RIPETIZIONIFUNC");
        },
        showCorsiDocentiFunc: function () {
            var self = this;

            if (self.utente.ruolo === 'Admin') {
                if (this.showCorsiDocenti === false)
                    self.showCorsiDocenti = true;
                self.showPrenotazioni = false;
                self.showRipetizioni = false;

                if (self.sessione !== 0) self.updateCorsiDocentiFunc();
                if (self.sessione !== 0) self.updateCorsiFunc();
                if (self.sessione !== 0) self.updateDocentiFunc();
            }
        },
        selectDocente: function (index, nome, cognome) {
            $("#btnDocente" + index).text(nome + " " + cognome);
        },
        updateCorsiDocentiFunc: function () {
            var self = this;

            $.get(self.sManageDB, {action: 'visualizzaCorsiDocenti', session: self.sessione}, function (data) {
                if (!data.hasOwnProperty("error")) self.corsidocenti = data;
                else self.manageError(data.error);
            });
        },
        updateCorsiFunc: function () {
            var self = this;

            $.get(self.sManageDB, {action: 'visualizzaCorsi', session: self.sessione}, function (data) {
                if (!data.hasOwnProperty("error")) self.corsi = data;
                else self.manageError(data.error);
            });
        },
        updateDocentiFunc: function () {
            var self = this;

            $.get(self.sManageDB, {action: 'visualizzaDocenti', session: self.sessione}, function (data) {
                if (!data.hasOwnProperty("error"))
                    self.docenti = data;
                else
                    self.manageError(data.error);
            });
        },
        updateRipetizioniFunc: function () {
            var self = this;

            $.get(self.sRipetizioni, {action: 'visualizzaRipetizioni', session: self.sessione}, function (data) {
                if (!data.hasOwnProperty("error"))
                    self.ripetizioni = data;
                else
                    self.manageError(data.error);
            });
        },
        updatePrenotazioniFunc: function () {
            var self = this;

            $.get(self.sPrenotazioni, {action: 'visualizzaPrenotazioni', session: self.sessione}, function (data) {
                if (!data.hasOwnProperty("error"))
                    self.prenotazioni = data;
                else
                    self.manageError(data.error);
            });
        },
        inserisciDocente: function () {
            var self = this;
            var nome = $("#inputNomeDocente").val();
            var cognome = $("#inputCognomeDocente").val();

            $.post(self.sManageDB, {
                action: "inserireDocente",
                session: self.sessione,
                nome: nome,
                cognome: cognome
            }, function (data) {
                if (!data.hasOwnProperty("error")) {
                    if (data === true) {
                        console.log("Ho inserito il docente: " + nome + " " + cognome);

                        const t1 = $("#toastEsitoPositivo");
                        const toastPos = new bootstrap.Toast(t1);

                        $("#toastPositivoTopic").text(" Docente inserito!");
                        $("#toastEsitoPositivoText").text("Ho inserito il docente: " + nome + " " + cognome);
                        toastPos.show();

                        self.updateDocentiFunc();
                    } else {
                        console.log("Non ho inserito il docente: " + nome + " " + cognome);

                        const t2 = $("#toastEsitoNegativo");
                        const toastNeg = new bootstrap.Toast(t2);

                        $("#toastNegativoTopic").text(" Docente non inserito!");
                        $("#toastEsitoNegativoText").text("Impossibile inserire il docente: " + nome + " " + cognome);
                        toastNeg.show();
                    }
                } else self.manageError(data.error);
            });
        },
        inserisciCorso: function () {
            var self = this;
            var titolo = $("#inputTitoloCorso").val();
            var cfu = $("#inputCfuCorso").val();

            $.post(self.sManageDB, {
                action: "inserireCorso",
                session: self.sessione,
                titolo: titolo,
                cfu: cfu
            }, function (data) {
                if (!data.hasOwnProperty("error")) {
                    if (data === true) {
                        console.log("Ho inserito il corso: " + titolo + " " + cfu + " cfu");

                        const t1 = $("#toastEsitoPositivo");
                        const toastPos = new bootstrap.Toast(t1);

                        $("#toastPositivoTopic").text(" Corso inserito!");
                        $("#toastEsitoPositivoText").text("Ho inserito il corso: " + titolo + " " + cfu + " cfu");
                        toastPos.show();

                        self.showCorsiDocentiFunc();
                    } else {
                        console.log("Non ho inserito il corso: " + titolo + " " + cfu + " cfu");

                        const t2 = $("#toastEsitoNegativo");
                        const toastNeg = new bootstrap.Toast(t2);

                        $("#toastNegativoTopic").text(" Corso non inserito!");
                        $("#toastEsitoNegativoText").text("Impossibile inserire il corso: " + titolo + ", " + cfu + " cfu");
                        toastNeg.show();
                    }
                } else self.manageError(data.error);
            });
        },
        rimuoviDocente: function (id) {
            console.log("Rimosso doc");
            self = this;

            $.post(self.sManageDB, {action: "rimuovereDocente", session: self.sessione, docente: id}, function (data) {
                if (!data.hasOwnProperty("error")) self.showCorsiDocentiFunc();
                else self.manageError(data.error);
            });
        },
        rimuoviCorso: function (titolo) {
            console.log("Rimozione corso");
            self = this;

            $.post(self.sManageDB, {action: "rimuovereCorso", session: self.sessione, corso: titolo}, function (data) {
                if (!data.hasOwnProperty("error")) self.showCorsiDocentiFunc();
                else self.manageError(data.error);
            });
        },
        assegnaDocenteCorso: function () {
            var self = this;
            const corso = self.titoloCorso;
            const docente = self.idDocente;

            $.post(self.sManageDB, {
                action: "inserireAssociazioneDocenteCorso",
                session: self.sessione,
                corso: corso,
                docente: docente
            }, function (data) {
                if (!data.hasOwnProperty("error")) {
                    if (data === true) {
                        console.log("Ho assegnato il corso " + corso + " al docente " + docente);

                        const t1 = $("#toastEsitoPositivo");
                        const toastPos = new bootstrap.Toast(t1);

                        $("#toastPositivoTopic").text(" Corso assegnato!");
                        $("#toastEsitoPositivoText").text("Ho assegnato il corso " + corso + " al docente " + docente);
                        toastPos.show();

                        self.showCorsiDocentiFunc();
                    } else {
                        console.log("Non ho assegnato il corso " + corso + " al docente " + docente);

                        const t2 = $("#toastEsitoNegativo");
                        const toastNeg = new bootstrap.Toast(t2);

                        $("#toastNegativoTopic").text(" Corso non assegnato!");
                        $("#toastEsitoNegativoText").text("Impossibile assegnare il corso " + corso + " al docente " + docente);
                        toastNeg.show();
                    }
                } else self.manageError(data.error);
            });
        },
        rimuoviAssegnamentoDocenteCorso: function (docente, corso) {
            var self = this;
            $.post(self.sManageDB, {
                action: "rimuovereAssociazioneDocenteCorso",
                session: self.sessione,
                docente: docente,
                corso: corso
            }, function (data) {
                if (!data.hasOwnProperty("error")) {
                    if (data === true)
                        self.showCorsiDocentiFunc();
                    else {
                        const t2 = $("#toastEsitoNegativo");
                        const toastNeg = new bootstrap.Toast(t2);

                        $("#toastNegativoTopic").text(" Associazione non rimossa!");
                        $("#toastEsitoNegativoText").text("Impossibile eliminare il docente " + docente + " dal corso " + corso);
                        toastNeg.show();
                    }
                } else self.manageError(data.error);
            });
        },
        prenotaRipetizioni: function () {
            const self = this;
            var docente = '';
            var errorExit = 0;

            jQuery.ajaxSetup({async: false});

            self.ripetizioni.forEach((ripetizione, index) => {
                var isChecked = '#checkRipetizione' + index;

                if ($(isChecked).is(":checked") && errorExit === 0) {
                    isChecked = '#floatingSelectDocenteRipetizione' + index;
                    docente = $(isChecked).val();

                    $.post(self.sRipetizioni, {
                        action: "attivarePrenotazione",
                        session: self.sessione,
                        ripetizione: ripetizione.id,
                        docente: docente
                    }, function (data) {
                        if (!data.hasOwnProperty("error")) {
                            if (data === true) {
                                console.log("Ho effettuato la prenotazione della ripetizione " + ripetizione + " con docente " + docente);

                                const t1 = $("#toastEsitoPositivo");
                                const toastPos = new bootstrap.Toast(t1);

                                $("#toastPositivoTopic").text(" Prenotazione confermata!");
                                $("#toastEsitoPositivoText").text("Ti sei prenotato per " + ripetizione.corso.titolo + " assieme al docente " + docente);
                                toastPos.show();

                                self.updateRipetizioniFunc();
                                self.updatePrenotazioniFunc();
                            } else {
                                console.log("Impossibile prenotare la ripetizione " + ripetizione + " corso: " + ripetizione.corso.titolo + " e docente: " + docente);

                                const t2 = $("#toastEsitoNegativo");
                                const toastNeg = new bootstrap.Toast(t2);

                                $("#toastNegativoTopic").text(" Prenotazione non effettuata!");
                                $("#toastEsitoNegativoText").text("Impossibile prenotare la ripetizione per il corso " + ripetizione.corso.titolo + " con docente " + docente);
                                toastNeg.show();
                            }
                        } else {
                            self.manageError(data.error);
                            errorExit = 1;
                        }
                    });
                }
            });

            jQuery.ajaxSetup({async: true});
        },
        disdiciPrenotazione(prenotazione) {
            var self = this;
            console.log("Voglio disdire la prenotazione della ripetizione: " + prenotazione.ripetizione.id + " con docente: " + prenotazione.docente.id + prenotazione.docente.nome + prenotazione.docente.cognome);
            $.post(self.sPrenotazioni, {
                action: "disdirePrenotazione",
                session: self.sessione,
                ripetizione: prenotazione.ripetizione.id,
                docente: prenotazione.docente.id,
                username: prenotazione.utente.username
            }, function (data) {
                if (!data.hasOwnProperty("error")) {
                    if (data === true) {
                        const t1 = $("#toastEsitoPositivo");
                        const toastPos = new bootstrap.Toast(t1);

                        $("#toastPositivoTopic").text(" Prenotazione disdetta!");
                        $("#toastEsitoPositivoText").text("La prenotazione al corso " + prenotazione.ripetizione.corso.titolo + " assieme al docente " + prenotazione.docente.nome + " " + prenotazione.docente.cognome + " è stata correttamente disdetta");
                        toastPos.show();

                        self.updateRipetizioniFunc();
                        self.updatePrenotazioniFunc();
                    } else {
                        const t2 = $("#toastEsitoNegativo");
                        const toastNeg = new bootstrap.Toast(t2);

                        $("#toastNegativoTopic").text(" Disdetta non effettuata!");
                        $("#toastEsitoNegativoText").text("Impossibile disdire la prenotazione per il corso " + prenotazione.ripetizione.corso.titolo + " con docente " + prenotazione.docente.id);
                        toastNeg.show();
                    }
                } else
                    self.manageError(data.error);
            });
        },
        segnaPrenotazioneEffettuata(prenotazione) {
            var self = this;
            console.log("Effettuare prenotazione della ripetizione: " + prenotazione.ripetizione.id + " con docente: " + prenotazione.docente.id + " " + prenotazione.docente.nome + prenotazione.docente.cognome);
            $.post(self.sPrenotazioni, {
                    action: "effettuarePrenotazione",
                    session: self.sessione,
                    ripetizione: prenotazione.ripetizione.id,
                    docente: prenotazione.docente.id
                }, function (data) {
                    if (!data.hasOwnProperty("error")) {
                        if (data === true) {
                            const t1 = $("#toastEsitoPositivo");
                            const toastPos = new bootstrap.Toast(t1);

                            $("#toastPositivoTopic").text(" Prenotazione effettuata!");
                            $("#toastEsitoPositivoText").text("La prenotazione al corso " + prenotazione.ripetizione.corso.titolo + " assieme al docente " + prenotazione.docente.id + "è stata effettuata");
                            toastPos.show();

                            self.updateRipetizioniFunc();
                            self.updatePrenotazioniFunc();
                        } else {
                            console.log("Impossibile effettuare la ripetizione " + prenotazione.ripetizione.id + " corso: " + prenotazione.ripetizione.corso.titolo + " e docente: " + prenotazione.docente.id);

                            const t2 = $("#toastEsitoNegativo");
                            const toastNeg = new bootstrap.Toast(t2);

                            $("#toastNegativoTopic").text(" Prenotazione non effettuata!");
                            $("#toastEsitoNegativoText").text("Impossibile effettuare la ripetizione " + prenotazione.ripetizione.id + " corso: " + prenotazione.ripetizione.corso.titolo + " e docente: " + prenotazione.docente.id);
                            toastNeg.show();
                        }
                    } else
                        self.manageError(data.error);
                }
            )
            ;
        },
        manageError(error) {
            const modalError = new bootstrap.Modal('#modalError', {
                backdrop: 'static',
                focus: false,
                keyboard: false
            });

            if (error.type === 'session') {
                $("#modalErrorTitle").text("Errore di Sessione");
                $("#modalErrorText").text(error.message);
            } else {
                $("#modalErrorStay").hide();
                $("#modalErrorTitle").text("Errore di Sistema");
                $("#modalErrorText").text(error.message);
            }

            modalError.show();
        },
        prolungaSessione() {
            var self = this;
            var user;

            jQuery.ajaxSetup({async: false});
            $.get("Login", {
                    action: 'login',
                    username: self.utente.username,
                    password: self.utente.password
                }, function (data) {
                    user = data;
                }
            );
            jQuery.ajaxSetup({async: true});

            console.log(user);
            if (user === 'Utente non riconosciuto') {
                const modalError = new bootstrap.Modal('#modalError', {
                    backdrop: 'static',
                    focus: false,
                    keyboard: false
                });

                $("#modalErrorTitle").text("Errore di Sessione");
                $("#modalErrorText").text(user);
                $("#modalErrorStay").hide();
                modalError.show();

                window.location.replace("login.html");
            } else {
                self.getSession();
                window.location.reload();
            }
        },
        enableCheckBox(index, type) {
            let id = "#checkRipetizione" + index;
            const box = $(id);

            alert("Switcho il box: " + id);

            if (type === 1) box.disabled = false;
            else {
                box.disabled = true;
                box.select = false;
            }
        }
    },
    mounted: function () {
        jQuery.ajaxSetup({async: false});
        this.getSession();
        this.showRipetizioniFunc();
        jQuery.ajaxSetup({async: true});
    }
});

$(function () {
    $(window).on("scroll", function () {
        if ($(window).scrollTop() > 50) {
            $(".header").addClass("header-motion");
            $(".btn-bd-primary").addClass("btn-bd-primary-motion");
            $(".btn-bd-primary").removeClass("btn-bd-primary");
        } else {
            $(".header").removeClass("header-motion");
            $(".btn-bd-primary-motion").addClass("btn-bd-primary");
            $(".btn-bd-primary-motion").removeClass("btn-bd-primary-motion");
        }
    });
});

function enableThaBox(self) {
    let id = self.id.substring(32, self.id.length);
    const box = document.getElementById("checkRipetizione"+id);

    if (self.value !== '') box.disabled = false;
    else {
        box.disabled = true;
        box.checked = false;
    }
}

function reload() {
    window.location.reload();
}