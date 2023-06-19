package com.example.prenotazioni.DAO;

public class Docente {
    private int id;
    private String nome;
    private String cognome;
    private boolean stato;

    public Docente(int id, String nome, String cognome, boolean stato) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.stato = stato;
    }

    public static Docente defaultDocente() {
        return new Docente(-1, null, null, false);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public boolean getStato() {
        return this.stato;
    }

    @Override
    public String toString() {
        return "{id = " + id + 
                        "\tnome = " + nome + 
                        "\tcognome = " + cognome + 
                        "\tstato = " + stato + "}\n";
    }

    public String simpleDocente() {
        return nome + " " + cognome;
    }

}
