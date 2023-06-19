package com.example.prenotazioni;

import com.example.prenotazioni.DAO.DAO;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Prenotazioni", value = "/Prenotazioni")
public class PrenotazioniServlet extends HttpServlet {
    private DAO dao = null;

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
        System.out.println("ShowPrenotazioniServlet: INIT");
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String database = ctx.getInitParameter("database");
        String user = ctx.getInitParameter("user");
        String password = ctx.getInitParameter("password");

        dao = new DAO(url, database, user, password);
    }

    /**
     * Imposta un file JSON visualizzando le ripetizioni:
     * se ruolo == Admin : mostra tutte le prenotazioni, disponibili e non
     * se ruolo == Cliente : mostra tutte e sole le ripetizioni disponibili per l'utente corrente
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action;
        HttpSession s;
        String ruolo, username, id, js = "";
        PrintWriter out;

        s = request.getSession(false);

        if (s != null) {
            ruolo = (String) s.getAttribute("ruolo");
            username = (String) s.getAttribute("username");
            id = request.getParameter("session");

            System.out.println(getServletContext().getContextPath());

            if (s.getId().equals(id)) {
                if (username != null && ruolo != null && (ruolo.equals("Admin") || ruolo.equals("Cliente"))) {
                    action = request.getParameter("action");

                    switch (action) {
                        case "visualizzaPrenotazioni":
                            js = this.visualizzaPrenotazioni(ruolo, username);
                            break;

                        case "disdirePrenotazione":
                            int ripetizione = Integer.parseInt(request.getParameter("ripetizione"));
                            int docente = Integer.parseInt(request.getParameter("docente"));
                            String utente = request.getParameter("username");
                            if (ripetizione >= 0 && docente >= 0 && utente != null)
                                js = this.disdirePrenotazione(utente, ripetizione, docente);
                            else
                                js = onError("general", "Controllare i parametri e riprovare");
                            break;

                        case "effettuarePrenotazione":
                            ripetizione = Integer.parseInt(request.getParameter("ripetizione"));
                            docente = Integer.parseInt(request.getParameter("docente"));
                            if (ripetizione >= 0 && docente >= 0)
                                js = this.effettuarePrenotazione(username, ripetizione, docente);
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
        System.out.println("Prenotazioni " + js);
        out.print(js);
        out.close();
    }

    private String visualizzaPrenotazioni(String ruolo, String username) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = ruolo.equals("Admin") ? gson.toJson(DAO.visualizzaPrenotazioni()) : gson.toJson(DAO.visualizzaPrenotazioni(username));

        return json;
    }

    private String disdirePrenotazione(String username, int ripetizione, int docente) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.cancellaPrenotazione(username, ripetizione, docente));

        return json;
    }

    private String effettuarePrenotazione(String username, int ripetizione, int docente) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.effettuaPrenotazione(username, ripetizione, docente));

        return json;
    }

    private String onError(String type, String s) {
        return "{\"error\":{\"type\":\"" + type + "\",\"message\":\"" + s + "\"}}";
    }
}