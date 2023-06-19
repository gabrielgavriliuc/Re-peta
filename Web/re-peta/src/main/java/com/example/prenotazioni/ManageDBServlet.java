package com.example.prenotazioni;

import com.example.prenotazioni.DAO.DAO;
import com.example.prenotazioni.DAO.Docente;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ManageDBServlet", value = "/ManageDBServlet")
public class ManageDBServlet extends HttpServlet {
    private DAO dao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public void init(ServletConfig conf) throws ServletException {
        super.init(conf);
        System.out.println("SONO IN INIT");
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String database = ctx.getInitParameter("database");
        String user = ctx.getInitParameter("user");
        String password = ctx.getInitParameter("password");

        dao = new DAO(url, database, user, password);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action;
        HttpSession s;
        String ruolo, id, js = "";
        PrintWriter out;

        s = request.getSession(false);

        if (s != null) {
            ruolo = (String) s.getAttribute("ruolo");
            id = request.getParameter("session");

            if (s.getId().equals(id)) {
                if (ruolo != null && ruolo.equals("Admin")) {
                    action = request.getParameter("action");

                    switch (action) {
                        case "visualizzaCorsiDocenti":
                            js = this.visualizzaCorsiDocenti();
                            break;

                        case "visualizzaCorsi":
                            js = this.visualizzaCorsi();
                            break;

                        case "visualizzaDocenti":
                            js = this.visualizzaDocenti();
                            break;

                        case "inserireDocente":
                            String nome = request.getParameter("nome");
                            String cognome = request.getParameter("cognome");
                            boolean stato = true;
                            if (nome != null && cognome != null)
                                js = this.inserireDocente(nome, cognome, true);
                            else
                                js = onError("general", "Controllare i parametri e riprovare");
                            break;

                        case "inserireCorso":
                            String titolo = request.getParameter("titolo");
                            int cfu = Integer.valueOf(request.getParameter("cfu"));
                            if (titolo != null && cfu > 0)
                                js = this.inserireCorso(titolo, cfu, true);
                            else
                                js = onError("general", "Controllare i parametri e riprovare");
                            break;

                        case "inserireAssociazioneDocenteCorso":
                            titolo = request.getParameter("corso");
                            int docente = Integer.valueOf(request.getParameter("docente"));
                            if (titolo != null && docente >= 0)
                                js = this.inserireAssociazioneDocenteCorso(titolo, docente);
                            else
                                js = onError("general", "Controllare i parametri e riprovare");
                            break;

                        case "rimuovereDocente":
                            docente = Integer.valueOf(request.getParameter("docente"));
                            if (docente >= 0)
                                js = this.rimuovereDocente(docente);
                            else
                                js = onError("general", "Controllare i parametri e riprovare");
                            break;

                        case "rimuovereCorso":
                            titolo = request.getParameter("corso");
                            if (titolo != null)
                                js = this.rimuovereCorso(titolo);
                            else
                                js = onError("general", "Controllare i parametri e riprovare");
                            break;

                        case "rimuovereAssociazioneDocenteCorso":
                            docente = Integer.valueOf(request.getParameter("docente"));
                            titolo = request.getParameter("corso");
                            if (docente >= 0 && titolo != null)
                                js = this.rimuovereAssociazioneDocenteCorso(docente, titolo);
                            else
                                js = onError("general", "Controllare i parametri e riprovare");
                            break;

                        default:
                            js = onError("general", "Operazione Sconosciuta");
                    }
                } else
                    js = onError("general", "Operazione non concessa: utente non riconosciuto!");
            } else
                js = onError("session", "Sessione non combacia con l'account!");
        } else
            js = onError("session", "Sessione nulla!");

        response.setContentType("application/json");
        out = response.getWriter();
        out.print(js);
        System.out.println(js);
        out.close();
    }

    private String visualizzaCorsiDocenti() {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.visualizzaCorsiDocenti());

        return json;
    }

    private String visualizzaCorsi() {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.visualizzaCorsi());

        return json;
    }

    private String visualizzaDocenti() {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.visualizzaDocenti());

        return json;
    }

    private String inserireDocente(String nome, String cognome, boolean stato) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.inserisciDocente(nome, cognome, stato));

        return json;
    }

    private String inserireCorso(String titolo, int cfu, boolean stato) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.inserisciCorso(titolo, cfu, stato));

        return json;
    }

    private String inserireAssociazioneDocenteCorso(String corso, int docente) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.assegnaDocente(corso, docente));

        return json;
    }

    private String rimuovereDocente(int id) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.rimuoviDocente(id));

        return json;
    }

    private String rimuovereCorso(String titolo) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.rimuoviCorso(titolo));

        return json;
    }

    private String rimuovereAssociazioneDocenteCorso(int id, String titolo) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.rimuoviDocenteCorso(id, titolo));

        return json;
    }

    private String onError(String type, String s) {
        return "{\"error\":{\"type\":\"" + type + "\",\"message\":\"" + s + "\"}}";
    }
}