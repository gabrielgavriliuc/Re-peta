package com.example.prenotazioni.DAO;

public class Prenotazione {
    private Ripetizione ripetizione;
    private Docente docente;
    private Utente utente;
    private String stato;

    public Prenotazione(Ripetizione ripetizione, Docente docente, Utente utente, String stato) {
        this.ripetizione = ripetizione;
        this.docente = docente;
        this.utente = utente;
        this.stato = stato;
    }

    public static Prenotazione defaultPrenotazione() {
        return new Prenotazione(null, null, null, null);
    }

    public Ripetizione getRipetizione() {
        return ripetizione;
    }

    public Docente getDocente() {
        return docente;
    }

    public Utente getUtente() {
        return utente;
    }

    public String getStato() {
        return stato;
    }

    @Override
    public String toString() {
        return "{Ripetizione." + ripetizione.simpleRipetizione() +
                "\tdocente = " + docente.simpleDocente() +
                "\tutente = " + utente.getUsername() +
                "\tstato = " + stato + "}\n";
    }

}
