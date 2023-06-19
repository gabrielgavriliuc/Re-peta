package com.example.prenotazioni.DAO;

public class Utente {
    private String username;
    private String nome;
    private String cognome;
    private String password;
    private String ruolo;

    public Utente(String username, String nome, String cognome, String password, String ruolo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.ruolo = ruolo;
    }

    public static Utente defaultUtente() {
        return new Utente(null, null, null, null, null);
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getPassword() {
        return password;
    }

    public String getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return "{username = " + username +
                "\tnome = " + nome +
                "\tcognome = " + cognome +
                "\tpassword = " + password +
                "\truolo = " + ruolo + "}\n";
    }
}
