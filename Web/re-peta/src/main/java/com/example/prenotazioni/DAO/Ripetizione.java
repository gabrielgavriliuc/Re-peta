package com.example.prenotazioni.DAO;

import java.util.List;

public class Ripetizione {
    private int id;
    private Corso corso;
    private List<Docente> docenti;
    private String giorno;
    private String oraInizio;
    private String oraFine;
    private int stato;

    public Ripetizione(int id, Corso corso, List<Docente> docenti, String giorno, String oraInizio, String oraFine, int stato) {
        this.id = id;
        this.corso = corso;
        this.docenti = docenti;
        this.giorno = giorno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.stato = stato;
    }

    public Ripetizione(int id, Corso corso, List<Docente> docenti, String giorno, String oraInizio, String oraFine) {
        this.id = id;
        this.corso = corso;
        this.docenti = docenti;
        this.giorno = giorno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.stato = 1;
    }

    public static Ripetizione defaultRipetizione() {
        return new Ripetizione(-1, null, null, null, null, null);
    }

    public int getId() {
        return this.id;
    }

    public Corso getCorso() {
        return corso;
    }

    public List<Docente> getDocenti() {
        return docenti;
    }

    public String getGiorno() {
        return giorno;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public String getOraFine() {
        return oraFine;
    }

    public int getStato() {
        return stato;
    }

    @Override
    public String toString() {
        return "{id = " + id +
                "\tcorso = " + corso.getTitolo() +
                "\tDocenti = {" + getDocentiSimple() +
                "}\tgiorno = " + giorno +
                "\toraInizio = " + oraInizio +
                "\toraFine = " + oraFine +
                "\tstato = " + stato + "}\n";
    }

    public String simpleRipetizione() {
        return "id = " + id +
                "\tcorso = " + corso.getTitolo();
    }

    private String getDocentiSimple() {
        String res = "";

        for (int i = 0; i < docenti.size(); i++) {
            if (i == docenti.size() - 1) {
                res += docenti.get(i).getNome() + " " + docenti.get(i).getCognome();
            } else
                res += docenti.get(i).getNome() + " " + docenti.get(i).getCognome() + ", ";
        }

        return res;
    }

}
