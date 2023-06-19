package com.example.prenotazioni.DAO;

public class Corso {
    private String titolo;
    private int CFU;
    private boolean stato;

    public Corso(String titolo, int CFU, boolean stato) {
        this.titolo = titolo;
        this.CFU = CFU;
        this.stato = stato;
    }

    public static Corso defaultCorso() {
        return new Corso(null, -1, false);
    }

    public String getTitolo() {
        return titolo;
    }

    public int getCFU() {
        return CFU;
    }

    public boolean getStato() {
        return stato;
    }

    @Override
    public String toString() {
        return "{titolo = " + titolo +
                "\tCFU = " + CFU +
                " \tstato = " + stato + "}\n";
    }
}
