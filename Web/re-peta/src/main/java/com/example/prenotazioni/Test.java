package com.example.prenotazioni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.prenotazioni.DAO.*;

/**
 * Test
 */
public class Test {

    public static void main(String[] args) {
        DAO dao = new DAO(null, null, null, null);
        initPrint();

        DAO.registerDriver();

//        DAO.clearDB();
//        System.out.println("Database pulito correttamente");
//
//        System.out.println(
//                "\n-------------------------------------------------------------------------------------- TEST INSERIMENTO --------------------------------------------------------------------------------------");
//        System.out.print("\tDocenti ");
        Docente d1 = new Docente(0, "Tony", "Effeil", true);
        Docente d2 = new Docente(1, "Emily", "Ratto-wkosky", true);
        Docente d3 = new Docente(2, "Luigi", "Eschimese", true);
        Docente d4 = new Docente(3, "Gianni", "Pagliaccio", true);
        Docente d5 = new Docente(4, "Rossella", "Brescia", true);
        Docente d6 = new Docente(5, "Lana", "Ruotes", true);
        Docente d7 = new Docente(6, "Katrina", "Stilton", true);
        Docente d8 = new Docente(7, "Chad", "Gipiti", true);
//        DAO.inserisciDocente(d1.getNome(), d1.getCognome(), d1.getStato());
//        DAO.inserisciDocente(d2.getNome(), d2.getCognome(), d2.getStato());
//        DAO.inserisciDocente(d3.getNome(), d3.getCognome(), d3.getStato());
//        DAO.inserisciDocente(d4.getNome(), d4.getCognome(), d4.getStato());
//        DAO.inserisciDocente(d5.getNome(), d5.getCognome(), d5.getStato());
//        DAO.inserisciDocente(d6.getNome(), d6.getCognome(), d6.getStato());
//        DAO.inserisciDocente(d7.getNome(), d7.getCognome(), d7.getStato());
//        DAO.inserisciDocente(d8.getNome(), d8.getCognome(), d8.getStato());
//        System.out.println("inseriti correttamente");
//
//        System.out.print("\tCorsi ");
        Corso c1 = new Corso("Pedofilia", 12, true);
        Corso c2 = new Corso("Musica", 6, true);
        Corso c3 = new Corso("Cucina", 9, true);
        Corso c4 = new Corso("Astrologia", 12, true);
        Corso c5 = new Corso("Inglese", 3, true);
//        DAO.inserisciCorso(c1.getTitolo(), c1.getCFU(), c1.getStato());
//        DAO.inserisciCorso(c2.getTitolo(), c2.getCFU(), c2.getStato());
//        DAO.inserisciCorso(c3.getTitolo(), c3.getCFU(), c3.getStato());
//        DAO.inserisciCorso(c4.getTitolo(), c4.getCFU(), c4.getStato());
//        DAO.inserisciCorso(c5.getTitolo(), c5.getCFU(), c5.getStato());
//        System.out.println("inseriti correttamente");
//
//        System.out.print("\tUtenti ");
        Utente u1 = new Utente("filippoJuvarra", "Filippo", "Juvarra", "tortina", "Cliente");
        Utente u2 = new Utente("reUmberto", "Re", "Umberto", "sovranitas", "Cliente");
//        DAO.inserisciUtente(u1.getUsername(), u1.getNome(), u1.getCognome(), u1.getPassword(), u1.getRuolo());
//        DAO.inserisciUtente(u2.getUsername(), u2.getNome(), u2.getCognome(), u2.getPassword(), u2.getRuolo());
//        System.out.println("inseriti correttamente");
//
//        System.out.print("\tRipetizioni ");
        List<Docente> ldr1 = Arrays.asList(d1, d2);
        List<Docente> ldr2 = Arrays.asList(d3, d4);
        List<Docente> ldr3 = Arrays.asList(d5, d6);
        List<Docente> ldr4 = Arrays.asList(d7, d8);
        Ripetizione r1 = new Ripetizione(0, c1, ldr1, "19/01/2023", "15", "16");
        Ripetizione r2 = new Ripetizione(1, c2, ldr2, "19/01/2023", "16", "17");
        Ripetizione r3 = new Ripetizione(2, c3, ldr3, "20/01/2023", "17", "18");
        Ripetizione r4 = new Ripetizione(3, c4, ldr4, "20/01/2023", "18", "19");
//        DAO.inserisciRipetizione(r1.getId(), r1.getCorso(), r1.getDocenti(), r1.getGiorno(), r1.getOraInizio(),
//                r1.getOraFine(), r1.getStato());
//        DAO.inserisciRipetizione(r2.getId(), r2.getCorso(), r2.getDocenti(), r2.getGiorno(), r2.getOraInizio(),
//                r2.getOraFine(), r2.getStato());
//        DAO.inserisciRipetizione(r3.getId(), r3.getCorso(), r3.getDocenti(), r3.getGiorno(), r3.getOraInizio(),
//                r3.getOraFine(), r3.getStato());
//        DAO.inserisciRipetizione(r4.getId(), r4.getCorso(), r4.getDocenti(), r4.getGiorno(), r4.getOraInizio(),
//                r4.getOraFine(), r4.getStato());
//        System.out.println("inseriti correttamente");
//
//        System.out.print("\tDocenti-Corsi ");
//        DAO.assegnaDocente(c1.getTitolo(), d5.getId());
//        DAO.assegnaDocente(c2.getTitolo(), d3.getId());
//        DAO.assegnaDocente(c3.getTitolo(), d5.getId());
//        DAO.assegnaDocente(c4.getTitolo(), d2.getId());
//        DAO.assegnaDocente(c5.getTitolo(), d1.getId());
//        DAO.assegnaDocente(c1.getTitolo(), d3.getId());
//        DAO.assegnaDocente(c2.getTitolo(), d2.getId());
//        DAO.assegnaDocente(c5.getTitolo(), d4.getId());
//        DAO.assegnaDocente(c4.getTitolo(), d7.getId());
//        System.out.println("inseriti correttamente");

        System.out.print("\tPrenotazioni ");
        DAO.attivaPrenotazione(u1.getUsername(), r1.getId(), ldr1.get(0).getId());
        DAO.attivaPrenotazione(u1.getUsername(), r4.getId(), ldr4.get(0).getId());
        DAO.attivaPrenotazione(u2.getUsername(), r3.getId(), ldr3.get(1).getId());
        DAO.attivaPrenotazione(u2.getUsername(), r2.getId(), ldr2.get(1).getId());
        System.out.println("inserite correttamente");

        System.out.println("-------------------------------------------------------------------------- TEST ALTERAZIONE--------------------------------------------------------------------------");
        System.out.print("\tEffettua prenotazione ");
        DAO.effettuaPrenotazione("filippoJuvarra", 0, 0);
        System.out.println();

        System.out.print("\tDisdici prenotazione ");
        DAO.cancellaPrenotazione("reUmberto", 2, 5);
        System.out.println();

        System.out.print("\tRimuovi corso ");
        DAO.rimuoviCorso("Musica");
        System.out.println();

        // System.out.print("\tRimuovi docente-corso");
        // DAO.rimuoviDocenteCorso(d7.getId(), "Astrologia");
        // System.out.println();

        // System.out.print("\tRimuovi docente");
        // DAO.rimuoviDocente(d7.getId());
        // System.out.println();

//        System.out.println(
//                "\n--------------------------------------------------------------------------------------- TEST VISUALIZZAZIONE ---------------------------------------------------------------------------------------");
//        List<Docente> docenti = DAO.visualizzaDocenti();
//        printTabella(docenti);
//
//        List<Corso> corsi = DAO.visualizzaCorsi();
//        printTabella(corsi);
//
//        List<Utente> utenti = DAO.visualizzaUtenti();
//        printTabella(utenti);
//
//        List<Ripetizione> ripetizioni = DAO.visualizzaRipetizioni();
//        printTabella(ripetizioni);
//
//        System.out.println("Ripetizioni disponibili ONLY");
//        List<Ripetizione> ripetizioniDisp = DAO.visualizzaRipetizioniDisponibili();
//        printTabella(ripetizioniDisp);
//
//        List<Pair<List<Docente>, Corso>> docentiCorsi = DAO.visualizzaDocentiCorsi();
//        printTabella(docentiCorsi);
//
//        List<Pair<Docente, Ripetizione>> docentiRipetizioni = DAO.visualizzaDocentiRipetizioni();
//        printTabella(docentiRipetizioni);
//
        List<Prenotazione> prenotazioni = DAO.visualizzaPrenotazioni();
        printTabella(prenotazioni);
//
//        System.out.println("Visualizzazione prenotazioni personali di " + u1.getUsername());
//        List<Prenotazione> prenotazioniPersonal = DAO.visualizzaPrenotazioni(u1.getUsername());
//        printTabella(prenotazioniPersonal);
//
//
//        System.out.println(
//                "------------------------------------------------------------------------- TEST LOGIN/REGISTRAZIONE ----------------------------------------------------------------------");
//        System.out.print("Login utente 1 ---> esito: ");
//        System.out.println(DAO.login(u1.getUsername(), u1.getPassword()) == null ? "Loggato correttamente" : "Errore");
//        System.out.print("Login utente 2 ---> esito: ");
//        System.out.println(DAO.login(u2.getUsername(), u2.getPassword()) == null ? "Loggato correttamente" : "Errore");
//        System.out.print("Login utente 1 con password errata ---> esito: ");
//        System.out
//                .println(DAO.login(u1.getUsername(), "frocetto2000") == null ? "Non loggato correttamente" : "Errore");
//        System.out.print("Login utente 2 con username errato ---> esito: ");
//        System.out.println(DAO.login("Filiberto", u2.getPassword()) == null ? "Non loggato correttamente" : "Errore");

    }

    /*
     * █▀█ █▀█ █ █▄░█ ▀█▀ █ █▄░█ █▀▀ █▀▄▀█ █▀▀ ▀█▀ █░█ █▀█ █▀▄ █▀
     * █▀▀ █▀▄ █ █░▀█ ░█░ █ █░▀█ █▄█ █░▀░█ ██▄ ░█░ █▀█ █▄█ █▄▀ ▄█
     */

    private static <T> void printTabella(List<T> tabella) {
        if (tabella.size() > 0) {
            switch (tabella.get(0).getClass().getName()) {
                case "DAO.Corso":
                    System.out.println("Corsi [");
                    break;
                case "DAO.Docente":
                    System.out.println("Docenti [");
                    break;
                case "DAO.Utente":
                    System.out.println("Utenti [");
                    break;
                case "DAO.Ripetizione":
                    System.out.println("Ripetizioni [");
                    break;
                case "DAO.Prenotazione":
                    System.out.println("Prenotazioni [");
                    break;
                case "DAO.Pair":
                    System.out.println("Accosciata mista [");
                    break;
                default:
                    System.out.println("Sottiletta [");
                    break;
            }

            for (T riga : tabella) {
                System.out.print("\t" + riga);
            }
            System.out.println("]\n");
        } else {
            System.out.println("\tTabella come le palle post sborrata (vuota)!");
        }
    }

    private static void initPrint() {
        System.out.println("███╗░░░███╗███████╗░██████╗░░█████╗░░██████╗███████╗░██████╗░██████╗░█████╗░");
        System.out.println("████╗░████║██╔════╝██╔════╝░██╔══██╗██╔════╝██╔════╝██╔════╝██╔════╝██╔══██╗");
        System.out.println("██╔████╔██║█████╗░░██║░░██╗░███████║╚█████╗░█████╗░░╚█████╗░╚█████╗░██║░░██║");
        System.out.println("██║╚██╔╝██║██╔══╝░░██║░░╚██╗██╔══██║░╚═══██╗██╔══╝░░░╚═══██╗░╚═══██╗██║░░██║");
        System.out.println("██║░╚═╝░██║███████╗╚██████╔╝██║░░██║██████╔╝███████╗██████╔╝██████╔╝╚█████╔╝");
        System.out.println("╚═╝░░░░░╚═╝╚══════╝░╚═════╝░╚═╝░░╚═╝╚═════╝░╚══════╝╚═════╝░╚═════╝░░╚════╝░");
        System.out.println("\n\n");

        System.out.println("Ciao negri, siamo pronti per il MEGATEST");
        System.out.println("----------------------------------------\n");
    }
}
