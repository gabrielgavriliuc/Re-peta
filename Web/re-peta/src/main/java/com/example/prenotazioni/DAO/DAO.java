package com.example.prenotazioni.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {

    private static String url1;
    private static String db;
    private static String db_username;
    private static String db_password;

    public DAO(String url, String database, String username, String password) {
        url1 = url == null ? "jdbc:mysql://localhost:3306/" : url;
        db = database == null ? "progetto_ium_tweb_2022_2023" : database;
        db_username = username == null ? "root" : username;
        db_password = password == null ? "" : password;
    }

    /**
     * Registra i driver per la comunicazione con il database remoto. Essenziale da
     * fare come prima azione, altrimenti le successive chiamate non funzioneranno
     */
    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato" );
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    /**
     * Effettua il login di un utente nel database
     *
     * @param username rappresenta il nome utente univoco scelto a fase di
     *                 registrazione
     * @param password rappresenta la password dell'utente
     * @return l'utente che ha tentato di fare l'accesso se esiste, null altrimenti
     */
    public static Utente login(String username, String password) {
        Utente utente = null;
        Connection conn = null;
        String query = "SELECT * FROM `Utenti` U WHERE U.username = ? AND U.password = ?";

        if (username != null || password != null) {
            try {
                conn = DriverManager.getConnection(url1 + db, db_username, db_password);

                PreparedStatement st = conn.prepareStatement(query);
                st.setString(1, username);
                st.setString(2, password);

                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    final String nome = rs.getString("Nome" );
                    final String cognome = rs.getString("Cognome" );
                    final String ruolo = rs.getString("Ruolo" );

                    utente = new Utente(username, nome, cognome, password, ruolo);
                }

                st.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return utente;
    }

    /**
     * Inserisce un nuovo utente all'interno del database
     *
     * @param username rappresenta lo username univoco dell'utente da inserire
     * @param nome     rappresenta il nome dell'utente
     * @param cognome  rappresenta il cognome dell'utente
     * @param password rappresenta la password dell'utente
     * @param ruolo    rappresenta il ruolo dell'utente [admin, user, cliente]
     * @return true se l'utente è stato inserito correttamente, false altrimenti
     */
    public static boolean inserisciUtente(String username, String password, String nome, String cognome, String ruolo) {
        Connection conn = null;
        boolean esito = false;
        String query = "INSERT INTO `Utenti` VALUES (?, ?, ?, ?, ?)";

        if (username != null || password != null || nome != null || cognome != null || ruolo != null) {
            if (ruolo.equals("Admin" ) || ruolo.equals("Cliente" ) || ruolo.equals("Guest" )) {

                try {
                    conn = DriverManager.getConnection(url1 + db, db_username, db_password);

                    PreparedStatement st = conn.prepareStatement(query);
                    st.setString(1, username);
                    st.setString(2, nome);
                    st.setString(3, cognome);
                    st.setString(4, password);
                    st.setString(5, ruolo);

                    st.executeUpdate();
                    st.close();
                    esito = true;
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                } finally {
                    try {
                        if (conn != null)
                            conn.close();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }

            } else
                System.out.println("'Ruolo' MUST be in ['Admin', 'Cliente', 'Guest']!" );
        }

        return esito;
    }

    /**
     * Inserisce un nuovo corso all'interno del database
     *
     * @param titolo rappresenta il titolo del corso
     * @param cfu    rappresenta il numero di cfu assegnati al corso
     * @param stato  rappresenta lo stato del corso
     * @return true se il corso è stato inserito correttamente, false altrimenti
     */
    public static boolean inserisciCorso(String titolo, int cfu, boolean stato) {
        Connection conn = null;
        boolean esito = false;
        String queryCheck = "SELECT * FROM `Corsi` WHERE Titolo = ? AND CFU = ? AND Stato = 0";
        String query = "INSERT INTO `Corsi` VALUES (?, ?, ?)";

        if (titolo == null || cfu < 0)
            return esito;

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(queryCheck);
            st.setString(1, titolo);
            st.setInt(2, cfu);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                st.close();
                st = conn.prepareStatement("UPDATE `Corsi` SET Stato = 1 WHERE Titolo = ?" );
                st.setString(1, titolo);
            } else {
                st.close();
                st = conn.prepareStatement(query);
                st.setString(1, titolo);
                st.setInt(2, cfu);
                st.setInt(3, (stato == true ? 1 : 0));
            }
            st.executeUpdate();
            st.close();
            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Inserisce un nuovo docente all'interno del database, registrandone i dati
     * anagrafici ed il codice identificativo univoco
     *
     * @param nome    rappresenta il nome del docente
     * @param cognome rappresenta il cognome del docente
     * @param stato   rappresenta lo stato del docente
     * @return true se il docente è stato inserito correttamente, false altrimenti
     */
    public static boolean inserisciDocente(String nome, String cognome, boolean stato) {
        Connection conn = null;
        boolean esito = false;
        String query = "INSERT INTO `docenti` (Nome, Cognome, Stato) VALUES (?, ?, ?)";

        if (nome == null || cognome == null)
            return esito;

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, nome);
            st.setString(2, cognome);
            st.setInt(3, (stato == true ? 1 : 0));

            st.executeUpdate();
            st.close();
            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Inserisce un nuovo utente all'interno del database
     *
     * @param id        rappresenta l'id della ripetizione da inserire
     * @param corso     rappresenta il corso di cui viene fatta la ripetizione
     * @param docenti   rappresenta la lista di docenti possibili per quella
     *                  ripetizione (dev'essercene almeno 1)
     * @param giorno    rappresenta il giorno designato per la ripetizione
     * @param oraInizio rappresenta l'orario di inizio per la ripetizione
     * @param oraFine   rappresenta l'orario di fine per la ripetizione
     * @return true se la ripetizione è stata inserita correttamente, false
     * altrimenti
     */
    public static boolean inserisciRipetizione(int id, Corso corso, List<Docente> docenti, String giorno,
                                               String oraInizio, String oraFine, int stato) {
        Connection conn = null;
        boolean esito = false;
        String query = "INSERT INTO `Ripetizioni` VALUES (?, ?, ?, ?, ?, ?)";
        String query2 = "INSERT INTO `Docenti-Ripetizioni` VALUES (?, ?)";

        if (id < 0 || corso == null || docenti == null || giorno == null || oraInizio == null || oraFine == null
                || stato < 0 || stato > 1)
            return esito;

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);
            st.setString(2, giorno);
            st.setString(3, oraInizio);
            st.setString(4, oraFine);
            st.setString(5, corso.getTitolo());
            st.setInt(6, stato);

            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query2);
            for (Docente doc : docenti) {
                st.setInt(1, doc.getId());
                st.setInt(2, id);

                st.executeUpdate();
            }

            st.close();
            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Permette di prenotare una ripetizione, impostandone lo stato e assegnando
     * l'utente protagonista della prenotazione
     *
     * @param utente      rappresenta lo username identificativo univoco dell'utente
     * @param ripetizione rappresenta il codice identificativo univoco della
     *                    ripetizione
     * @param docente     rappresenta il codice identificativo univoco del docente
     * @return true se la prenotazione è stata effettuata correttamente, false
     * altrimenti
     */
    public static boolean attivaPrenotazione(String utente, int ripetizione, int docente) {
        Connection conn = null;
        boolean esito = false;
        String query = "INSERT INTO `Prenotazioni` VALUES(?, ?, ?, ?)";
        String queryCheck = "SELECT * FROM `Prenotazioni` WHERE Ripetizione = ? AND Docente = ? AND Stato = 'Cancellata' AND Utente = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(queryCheck);
            st.setInt(1, ripetizione);
            st.setInt(2, docente);
            st.setString(3, utente);

            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                st.close();
                st = conn.prepareStatement("UPDATE  Prenotazioni P SET P.Stato = 'Attivata' WHERE Ripetizione = ? AND Docente = ? AND Utente = ?" );
                st.setInt(1, ripetizione);
                st.setInt(2, docente);
                st.setString(3, utente);
            } else {
                st.close();
                st = conn.prepareStatement(query);
                st.setInt(1, ripetizione);
                st.setInt(2, docente);
                st.setString(3, utente);
                st.setString(4, "Attivata" );
            }

            st.executeUpdate();
            st.close();
            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Permette di cancellare la prenotazione di una ripetizione, cambiandone lo
     * stato e mantenendo la persistenza
     *
     * @param utente      rappresenta lo username identificativo univoco dell'utente
     * @param ripetizione rappresenta il codice identificativo univoco della
     *                    ripetizione
     * @param docente     rappresenta il codice identificativo univoco del docente
     * @return true se la prenotazione è stata cancellata correttamente, false
     * altrimenti
     */
    public static boolean cancellaPrenotazione(String utente, int ripetizione, int docente) {
        Connection conn = null;
        boolean esito = false;
        String query = "UPDATE `Prenotazioni` P SET P.Stato = ? WHERE P.Ripetizione = ? AND P.Docente = ? AND P.Utente = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, "Cancellata" );
            st.setInt(2, ripetizione);
            st.setInt(3, docente);
            st.setString(4, utente);

            st.executeUpdate();
            st.close();
            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Permette di segnare la prenotazione di una ripetizione come effettuata, nel
     * senso che l'utente ha effettivamente assistito alla ripetizione per cui si
     * era prenotato, cambiandone lo stato e mantenendo la persistenza
     *
     * @param utente      rappresenta lo username identificativo univoco dell'utente
     * @param ripetizione rappresenta il codice identificativo univoco della
     *                    ripetizione
     * @param docente     rappresenta il codice identificativo univoco del docente
     * @return true se la prenotazione è stata aggiornata correttamente, false
     * altrimenti
     */
    public static boolean effettuaPrenotazione(String utente, int ripetizione, int docente) {
        Connection conn = null;
        boolean esito = false;
        String query = "UPDATE `Prenotazioni` P SET P.Stato = ? WHERE P.Ripetizione = ? AND P.Docente = ? AND P.Utente = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, "Effettuata" );
            st.setInt(2, ripetizione);
            st.setInt(3, docente);
            st.setString(4, utente);

            st.executeUpdate();
            st.close();
            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Permette di annullare una prenotazione precedentemente fatta per una
     * ripetizione, impostandone lo stato e rimuovendo l'assegnamento dell'utente
     * protagonista della prenotazione
     *
     * @param utente      rappresenta il codice identificativo univoco dell'utente
     * @param ripetizione rappresenta il codice identificativo univoco della
     *                    ripetizione
     * @return true se la prenotazione è stata annullata correttamente, false
     * altrimenti
     */
    public static boolean annullaPrenotazione(String utente, int ripetizione, int docente) {
        Connection conn = null;
        boolean esito = false;
        String query = "DELETE FROM Prenotazioni P WHERE P.`Ripetizione` = ? AND P.`Utente` = ? AND P.`Docente` = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, ripetizione);
            st.setString(2, utente);
            st.setInt(3, docente);

            st.executeUpdate();
            st.close();
            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Visualizza gli elementi della tabella 'Ripetizioni' che fanno riferimento
     * ad un determinato corso
     *
     * @param corso rappresenta il codice identificativo univoco del corso
     * @return una lista contenente tutti gli elementi di 'Ripetizioni'
     * corrispondenti al corso
     */
    public static List<Ripetizione> visualizzaRipetizioni(String corso) {
        Connection conn = null;
        String query = "SELECT * FROM Ripetizioni R WHERE R.Corso = ?";
        List<Ripetizione> ripetizioni = new ArrayList<Ripetizione>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, corso);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final int id = rs.getInt("Id" );
                final String giorno = rs.getString("Giorno" );
                final String oraInizio = rs.getString("OraInizio" );
                final String oraFine = rs.getString("OraFine" );
                final int stato = rs.getInt("Stato" );

                List<Docente> docenti = visualizzaDocentiByRipetizione(id, 1);
                Corso cor = visualizzaCorso(corso);

                ripetizioni.add(new Ripetizione(id, cor, docenti, giorno, oraInizio, oraFine, stato));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return ripetizioni;
    }

    /**
     * Aggiorna il valore relativo al Docente per un determinato Corso, assegnando
     * di fatto il docente al corso
     *
     * @param corso   rappresenta il codice identificativo univoco del corso
     * @param docente rappresenta il codice identificativo univoco del docente
     * @return true se il docente è stato assegnato correttamente, false
     * altrimenti
     */
    public static boolean assegnaDocente(String corso, int docente) {
        Connection conn = null;
        boolean esito = false;
        String queryCheck = "SELECT * FROM `Docenti-Corsi` WHERE Corso = ? AND Docente = ? AND Stato = 0";
        String queryDC = "INSERT INTO `Docenti-Corsi` VALUES (?, ?, ?)";
        String queryDR = "INSERT INTO `Docenti-Ripetizioni` (Docente, Ripetizione) SELECT D.id, R.Id FROM Ripetizioni R, Docenti D WHERE R.Corso = ? AND D.id = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(queryCheck);
            st.setString(1, corso);
            st.setInt(2, docente);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                st.close();
                st = conn.prepareStatement("UPDATE `Docenti-Corsi` DC SET DC.Stato = 1 WHERE DC.Docente = ? AND DC.Corso = ?" );
                st.setInt(1, docente);
                st.setString(2, corso);
            } else {
                st.close();
                st = conn.prepareStatement(queryDC);
                st.setString(1, corso);
                st.setInt(2, docente);
                st.setInt(3, 1);
            }
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(queryDR);
            st.setInt(2, docente);
            st.setString(1, corso);
            st.executeUpdate();
            st.close();

            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Aggiorna il valore relativo al Docente per un determinato Corso, rimuovendo
     * di fatto il docente dal corso
     *
     * @param corso   rappresenta il titolo identificativo univoco del corso
     * @param docente rappresenta il codice identificativo univoco del docente
     * @return true se il docente è stato rimosso correttamente, false altrimenti
     */
    public static boolean rimuoviDocenteCorso(int docente, String corso) {
        Connection conn = null;
        boolean esito = false;
        String query_DC = "UPDATE `Docenti-Corsi` DC SET DC.Stato = ? WHERE DC.Corso = ? AND DC.Docente = ?";
        String query_P = "UPDATE Prenotazioni P JOIN Ripetizioni R ON P.Ripetizione = R.Id SET P.Stato = ? WHERE R.Corso = ? AND P.Docente = ?";
        String query_DR = "DELETE DR FROM `Docenti-Ripetizioni` DR JOIN Ripetizioni R ON DR.Ripetizione = R.Id WHERE R.Corso = ? AND DR.Docente = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query_DC);
            st.setInt(1, 0);
            st.setString(2, corso);
            st.setInt(3, docente);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_P);
            st.setString(1, "Cancellata" );
            st.setString(2, corso);
            st.setInt(3, docente);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_DR);
            st.setString(1, corso);
            st.setInt(2, docente);
            st.executeUpdate();
            st.close();

            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Aggiorna il valore relativo allo stato del Docente, rimuovendo di fatto il
     * docente dall'incarico
     *
     * @param docente rappresenta il codice identificativo univoco del docente
     * @return true se il docente è stato rimosso correttamente, false altrimenti
     */
    public static boolean rimuoviDocente(int docente) {
        Connection conn = null;
        boolean esito = false;
        String query_D = "UPDATE Docenti D SET D.Stato = ? WHERE D.Id = ?";
        String query_DC = "UPDATE `Docenti-Corsi` DC SET DC.Stato = ? WHERE DC.Docente = ?";
        String query_P = "UPDATE Prenotazioni P JOIN Ripetizioni R ON P.Ripetizione = R.Id SET P.Stato = ? WHERE P.Docente = ?";
        String query_DR = "DELETE DR FROM `Docenti-Ripetizioni` DR JOIN Ripetizioni R ON DR.Ripetizione = R.Id WHERE DR.Docente = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query_D);
            st.setInt(1, 0);
            st.setInt(2, docente);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_DC);
            st.setInt(1, 0);
            st.setInt(2, docente);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_P);
            st.setString(1, "Cancellata" );
            st.setInt(2, docente);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_DR);
            st.setInt(1, docente);
            st.executeUpdate();
            st.close();

            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Aggiorna il valore dello stato relativo al Corso, rimuovendo di fatto il
     * corso dagli insegnamenti disponibili
     *
     * @param titolo rappresenta il codice identificativo univoco del corso
     * @return true se il corso è stato rimosso correttamente, false altrimenti
     */
    public static boolean rimuoviCorso(String titolo) {
        Connection conn = null;
        boolean esito = false;
        String query_C = "UPDATE `Corsi` C SET C.`Stato` = ? WHERE C.`Titolo` = ?";
        String query_DC = "UPDATE `Docenti-Corsi` DC SET `Stato` = ? WHERE DC.`Corso` = ?";
        String query_R = "UPDATE Ripetizioni R SET R.Stato = ? WHERE R.Corso = ?";
        String query_DR = "DELETE DR FROM `Docenti-Ripetizioni` DR JOIN Docenti D ON DR.Docente = D.Id WHERE D.Stato = ?";
        String query_P = "UPDATE `Prenotazioni` P join `Ripetizioni` R on P.Ripetizione = R.id SET P.Stato = ? WHERE R.Corso = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query_C);
            st.setInt(1, 0);
            st.setString(2, titolo);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_DC);
            st.setInt(1, 0);
            st.setString(2, titolo);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_R);
            st.setInt(1, 0);
            st.setString(2, titolo);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_DR);
            st.setInt(1, 0);
            st.executeUpdate();
            st.close();

            st = conn.prepareStatement(query_P);
            st.setString(1, "Cancellata" );
            st.setString(2, titolo);
            st.executeUpdate();
            st.close();

            esito = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return esito;
    }

    /**
     * Visualizza l'intero contenuto della tabella 'Docenti-Corsi' del database
     *
     * @return una lista contenente tutti i docenti ed i rispettivi corsi
     */
    public static List<Pair<List<Docente>, Corso>> visualizzaDocentiCorsi() {
        Connection conn = null;
        List<Pair<List<Docente>, Corso>> doc_cor = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `Docenti-Corsi` ORDER BY Corso" );

            while (rs.next()) {
                final String titolo = rs.getString("Corso" );
                final int id = rs.getInt("Docente" );

                Corso corso = visualizzaCorso(titolo);
                Docente docente = visualizzaDocente(id);

                if (doc_cor.size() > 0 && corso.getTitolo().equals(doc_cor.get(doc_cor.size() - 1).getC().getTitolo()))
                    doc_cor.get(doc_cor.size() - 1).getD().add(docente);
                else {
                    List<Docente> doc = new ArrayList<>();

                    if (docente != null) {
                        doc.add(docente);
                        doc_cor.add(new Pair<List<Docente>, Corso>(doc, corso));
                    }
                }
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return doc_cor;
    }

    /**
     * Visualizza tutti i corsi contenuti nel db e gli eventuali docenti assegnati
     *
     * @return una lista contenente tutti i corsi ed i rispettivi docenti
     */
    public static List<Pair<List<Docente>, Corso>> visualizzaCorsiDocenti() {
        Connection conn = null;
        List<Pair<List<Docente>, Corso>> doc_cor = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT dc.Docente, dc.Stato, c.Titolo FROM `Docenti-Corsi` dc RIGHT JOIN `Corsi` c on dc.Corso = c.Titolo ORDER BY c.Titolo" );

            while (rs.next()) {
                final String titolo = rs.getString("Titolo" );
                final String id = rs.getString("Docente" );
                final String stato = rs.getString("Stato" );

                Corso corso = visualizzaCorso(titolo);
                Docente docente = null;

                if (id != null && stato != null && stato.equals("1" )) {
                    docente = visualizzaDocente(Integer.valueOf(id));
                }

                if (doc_cor.size() > 0 && titolo.equals(doc_cor.get(doc_cor.size() - 1).getC().getTitolo())) {
                    if (docente != null)
                        doc_cor.get(doc_cor.size() - 1).getD().add(docente);
                } else {
                    List<Docente> doc = new ArrayList<>();

                    if (docente != null)
                        doc.add(docente);
                    doc_cor.add(new Pair<List<Docente>, Corso>(doc, corso));
                }
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return doc_cor;
    }

    /**
     * Visualizza l'intero contenuto della tabella 'Docenti-Ripetizioni' del
     * database
     *
     * @return una lista contenente tutti i docenti e le rispettive ripetizioni
     */
    public static List<Pair<Docente, Ripetizione>> visualizzaDocentiRipetizioni() {
        Connection conn = null;
        List<Pair<Docente, Ripetizione>> doc_cor = new ArrayList<Pair<Docente, Ripetizione>>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM `Docenti-Ripetizioni`" );
            while (rs.next()) {
                final int id_rip = rs.getInt("Ripetizione" );
                final int id_doc = rs.getInt("Docente" );

                Ripetizione ripetizione = visualizzaRipetizione(id_rip);
                Docente docente = visualizzaDocente(id_doc);

                doc_cor.add(new Pair<Docente, Ripetizione>(docente, ripetizione));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return doc_cor;
    }

    /**
     * Visualizza l'intero contenuto della tabella 'corsi' del database
     *
     * @return una lista contenente tutti i corsi
     */
    public static List<Corso> visualizzaCorsi() {
        Connection conn = null;
        List<Corso> corsi = new ArrayList<Corso>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Corsi" );
            while (rs.next()) {
                final String titolo = rs.getString("Titolo" );
                final int cfu = rs.getInt("CFU" );
                final boolean stato = rs.getInt("Stato" ) == 0 ? false : true;

                corsi.add(new Corso(titolo, cfu, stato));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return corsi;
    }

    /**
     * Visualizza l'intero contenuto della tabella 'Docenti' del database
     *
     * @return una lista contenente tutti i docenti
     */
    public static List<Docente> visualizzaDocenti() {
        Connection conn = null;
        List<Docente> docenti = new ArrayList<Docente>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Docenti" );
            while (rs.next()) {
                final int id = rs.getInt("Id" );
                final String nome = rs.getString("Nome" );
                final String cognome = rs.getString("Cognome" );
                final boolean stato = rs.getInt("Stato" ) == 0 ? false : true;

                docenti.add(new Docente(id, nome, cognome, stato));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return docenti;
    }

    /**
     * Visualizza tutti i docenti associati ad una certa ripetizione, identificata
     * da id
     *
     * @param id_ripetizione rappresenta l'identificatore univoco della
     *                       ripetizione
     * @return una lista contenente tutti i 'Docenti' associati
     */
    private static List<Docente> visualizzaDocentiByRipetizione(int id_ripetizione, int check) {
        Connection conn = null;
        String query;
        List<Docente> docenti = new ArrayList<Docente>();

        if (check == 0)
            query = "SELECT DISTINCT DR.* FROM `Docenti-Ripetizioni` DR LEFT JOIN Prenotazioni P ON DR.Docente = P.Docente AND DR.Ripetizione = P.Ripetizione WHERE DR.Ripetizione = ? AND P.Stato = 'Cancellata' OR DR.Ripetizione = ? AND DR.Docente NOT IN (SELECT P2.Docente FROM Prenotazioni P2 WHERE P2.Ripetizione = DR.Ripetizione);";
        else
            query = "SELECT * FROM `Docenti-Ripetizioni` DR WHERE DR.Ripetizione = ?";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id_ripetizione);
            if (check == 0)
                st.setInt(2, id_ripetizione);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final int id_docente = rs.getInt("Docente" );

                Docente docente = visualizzaDocente(id_docente);

                docenti.add(docente);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return docenti;
    }

    /**
     * Visualizza l'intero contenuto della tabella 'Ripetizioni' del database
     *
     * @return una stringa contenente tutti gli elementi di 'Ripetizioni'
     */
    public static List<Ripetizione> visualizzaRipetizioni() {
        Connection conn = null;
        List<Ripetizione> ripetizioni = new ArrayList<Ripetizione>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Ripetizioni" );
            while (rs.next()) {
                final int id = rs.getInt("Id" );
                final String giorno = rs.getString("Giorno" );
                final String oraInizio = rs.getString("OraInizio" );
                final String oraFine = rs.getString("OraFine" );
                final String titoloCorso = rs.getString("Corso" );
                final int stato = rs.getInt("Stato" );

                final List<Docente> docenti = visualizzaDocentiByRipetizione(id, 1);
                final Corso corso = visualizzaCorso(titoloCorso);

                ripetizioni.add(new Ripetizione(id, corso, docenti, giorno, oraInizio, oraFine, stato));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return ripetizioni;
    }

    /**
     * Visualizza tutte le tuple presenti in 'Ripetizioni' che non sono state ancora
     * prenotate da nessun utente
     *
     * @return una lista contenente tutti gli elementi di 'Ripetizioni' che non
     * sono stati ancora prenotati
     */
    public static List<Ripetizione> visualizzaRipetizioniDisponibili() {
        Connection conn = null;
        List<Ripetizione> ripetizioni = new ArrayList<Ripetizione>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT distinct R.Id, R.Giorno, R.OraInizio, R.OraInizio, R.OraFine, R.Corso, R.Stato FROM Ripetizioni R JOIN `Docenti-Ripetizioni` DR ON R.id = DR.Ripetizione WHERE R.Stato = 1 AND DR.docente NOT IN (SELECT P.docente FROM Prenotazioni P WHERE P.Ripetizione = R.Id AND P.Stato = 'Attivata' OR P.Stato = 'Effettuata')" );

            while (rs.next()) {
                final int id = rs.getInt("Id" );
                final String giorno = rs.getString("Giorno" );
                final String oraInizio = rs.getString("OraInizio" );
                final String oraFine = rs.getString("OraFine" );
                final String titoloCorso = rs.getString("Corso" );
                final int stato = rs.getInt("Stato" );

                final List<Docente> docenti = visualizzaDocentiByRipetizione(id, 0);
                final Corso corso = visualizzaCorso(titoloCorso);

                if (docenti.size() > 0)
                    ripetizioni.add(new Ripetizione(id, corso, docenti, giorno, oraInizio, oraFine, stato));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return ripetizioni;
    }

    /**
     * Visualizza la ripetizione corrispondente all'identificatore fornito
     *
     * @return la ripetizione cercata se esiste, null altrimenti
     */
    public static Ripetizione visualizzaRipetizione(int id) {
        Connection conn = null;
        String query = "SELECT * FROM Ripetizioni R WHERE R.Id = ?";
        Ripetizione ripetizione = null;

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);
            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final String giorno = rs.getString("Giorno" );
                final String oraInizio = rs.getString("OraInizio" );
                final String oraFine = rs.getString("OraFine" );
                final String titoloCorso = rs.getString("Corso" );
                final int stato = rs.getInt("Stato" );

                final List<Docente> docenti = visualizzaDocentiByRipetizione(id, 1);
                final Corso corso = visualizzaCorso(titoloCorso);

                ripetizione = new Ripetizione(id, corso, docenti, giorno, oraInizio, oraFine, stato);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return ripetizione;
    }

    /**
     * Visualizza l'intero contenuto della tabella 'Prenotazioni' del database
     *
     * @return una stringa contenente tutti gli elementi di 'Prenotazioni'
     */
    public static List<Prenotazione> visualizzaPrenotazioni() {
        Connection conn = null;
        List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Prenotazioni" );
            while (rs.next()) {
                final Ripetizione ripetizione = visualizzaRipetizione(rs.getInt("Ripetizione" ));
                final Utente utente = visualizzaUtente(rs.getString("Utente" ));
                final Docente docente = visualizzaDocente(rs.getInt("Docente" ));
                final String stato = rs.getString("Stato" );

                prenotazioni.add(new Prenotazione(ripetizione, docente, utente, stato));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return prenotazioni;
    }

    /**
     * Visualizza tutte le prenotazioni effettuate dall'utente specificato
     *
     * @return una stringa contenente tutti gli elementi di 'Prenotazioni'
     */
    public static List<Prenotazione> visualizzaPrenotazioni(String username) {
        Connection conn = null;
        List<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
        String query = "SELECT * FROM Prenotazioni P WHERE P.Utente = ? AND P.Stato != 'Cancellata'";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, username);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final Ripetizione ripetizione = visualizzaRipetizione(rs.getInt("Ripetizione" ));
                final Utente utente = visualizzaUtente(username);
                final Docente docente = visualizzaDocente(rs.getInt("Docente" ));
                final String stato = rs.getString("Stato" );

                prenotazioni.add(new Prenotazione(ripetizione, docente, utente, stato));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return prenotazioni;
    }

    /**
     * Visualizza l'intero contenuto della tabella 'Utenti' del database
     *
     * @return una lista contenente tutti gli elementi di 'Utenti'
     */
    public static List<Utente> visualizzaUtenti() {
        Connection conn = null;
        List<Utente> utenti = new ArrayList<Utente>();

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Utenti" );
            while (rs.next()) {
                final String username = rs.getString("Username" );
                final String nome = rs.getString("Nome" );
                final String cognome = rs.getString("Cognome" );
                final String password = rs.getString("Password" );
                final String ruolo = rs.getString("Ruolo" );

                utenti.add(new Utente(username, nome, cognome, password, ruolo));
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return utenti;
    }

    /**
     * Visualizza il corso relativo al titolo
     *
     * @param titolo rappresenta il titolo del corso da visualizzare
     * @return il corso relativo al titolo se esiste, null altrimenti
     */
    private static Corso visualizzaCorso(String titolo) {
        Connection conn = null;
        String query = "SELECT * FROM Corsi C WHERE C.Titolo = ?";
        Corso corso = null;

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, titolo);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final int cfu = rs.getInt("cfu" );
                final boolean stato = rs.getInt("Stato" ) == 0 ? false : true;

                corso = new Corso(titolo, cfu, stato);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return corso;
    }

    /**
     * Visualizza il docente relativo al id
     *
     * @param id rappresenta l'identificatore del docente da visualizzare
     * @return il docente relativo al id se esiste, null altrimenti
     */
    private static Docente visualizzaDocente(int id) {
        Connection conn = null;
        String query = "SELECT * FROM Docenti D WHERE D.Id = ?";
        Docente docente = null;

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                final String nome = rs.getString("Nome" );
                final String cognome = rs.getString("Cognome" );
                final boolean stato = rs.getInt("Stato" ) == 0 ? false : true;

                docente = new Docente(id, nome, cognome, stato);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return docente;
    }

    /**
     * Visualizza le informazioni dell'utente relativo a username
     *
     * @param username rappresenta il nome utente dell'utente da visualizzare
     * @return le informazioni dell'utente relativo a username se esiste, null
     * altrimenti
     */
    private static Utente visualizzaUtente(String username) {
        Connection conn = null;
        String query = "SELECT * FROM Utenti U WHERE U.Username = ?";
        Utente utente = null;

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, username);

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                final String nome = rs.getString("Nome" );
                final String cognome = rs.getString("Cognome" );
                final String password = rs.getString("Password" );
                final String ruolo = rs.getString("Ruolo" );

                utente = new Utente(username, nome, cognome, password, ruolo);
            }
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return utente;
    }

    /*
     * ------------- Pulizia Database ----------------
     */

    /**
     * Rimuove tutte le righe da tutte le tabelle presenti nel Database
     */
    public static void clearDB() {
        clearDocentiRipetizioni();
        clearDocentiCorsi();
        clearPrenotazioni();
        clearRipetizioni();
        clearCorsi();
        clearDocenti();
        clearUtenti();
    }

    /**
     * Rimuove tutte le righe dalla tabella prenotazioni
     */
    public static void clearDocentiRipetizioni() {
        Connection conn = null;
        String query = "DELETE FROM `docenti-ripetizioni`";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Rimuove tutte le righe dalla tabella docenti-corsi
     */
    public static void clearDocentiCorsi() {
        Connection conn = null;
        String query = "DELETE FROM `docenti-corsi`";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Rimuove tutte le righe dalla tabella prenotazioni
     */
    public static void clearPrenotazioni() {
        Connection conn = null;
        String query = "DELETE FROM prenotazioni";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Rimuove tutte le righe dalla tabella ripetizioni
     */
    public static void clearRipetizioni() {
        Connection conn = null;
        String query = "DELETE FROM ripetizioni";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Rimuove tutte le righe dalla tabella corsi
     */
    public static void clearCorsi() {
        Connection conn = null;
        String query = "DELETE FROM corsi";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Rimuove tutte le righe dalla tabella docenti
     */
    public static void clearDocenti() {
        Connection conn = null;
        String query = "DELETE FROM docenti";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Rimuove tutte le righe dalla tabella utenti
     */
    public static void clearUtenti() {
        Connection conn = null;
        String query = "DELETE FROM utenti";

        try {
            conn = DriverManager.getConnection(url1 + db, db_username, db_password);

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}