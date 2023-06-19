package com.example.prenotazioni;

import com.example.prenotazioni.DAO.DAO;
import com.google.gson.Gson;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Ripetizioni", value = "/Ripetizioni")
public class RipetizioniServlet extends HttpServlet {
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
        System.out.println("RIPETIZIONI: INIT");
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String database = ctx.getInitParameter("database");
        String user = ctx.getInitParameter("user");
        String password = ctx.getInitParameter("password");

        dao = new DAO(url, database, user, password);
    }

    /**
     * Imposta un file JSON visualizzando le ripetizioni:
     * se ruolo == Admin : mostra tutte le ripetizioni, disponibili e non
     * se ruolo == Cliente : mostra tutte e sole le ripetizioni disponibili (non prenotate)
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action;
        HttpSession s;
        String ruolo, username, id, js = "";
        PrintWriter out;

        s = request.getSession(false);

        System.out.println("HTTP: " + request.isRequestedSessionIdFromURL());
        System.out.println("COOKIE: " + request.isRequestedSessionIdFromCookie());
        System.out.println("VALID: " + request.isRequestedSessionIdValid());

        if (s != null) {
            ruolo = (String) s.getAttribute("ruolo");
            username = (String) s.getAttribute("username");
            id = request.getParameter("session");

            if (s.getId().equals(id)) {
                if (username != null && ruolo != null && (ruolo.equals("Admin") || ruolo.equals("Cliente") || ruolo.equals("Guest"))) {
                    action = request.getParameter("action");

                    switch (action) {
                        case "visualizzaRipetizioni":
                            js = this.visualizzaRipetizioni(ruolo);
                            break;

                        case "visualizzaRipetizioniCorso":
                            String corso = request.getParameter("corso");
                            if (corso != null)
                                js = this.visualizzaRipetizioniCorso(corso);
                            else
                                js = onError("general", "Parametro corso non definito");
                            break;

                        case "attivarePrenotazione":
                            int ripetizione = Integer.parseInt(request.getParameter("ripetizione"));
                            int docente = Integer.parseInt(request.getParameter("docente"));
                            if (ripetizione >= 0 && docente >= 0 && username != null)
                                js = this.attivarePrenotazione(username, ripetizione, docente);
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

        System.out.println(request.getParameter("session"));
        response.setContentType("application/json");
        out = response.getWriter();
        System.out.println("RipetizioniServlet " + js);
        out.print(js);
        out.close();
    }

    private String visualizzaRipetizioniCorso(String corso) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.visualizzaRipetizioni(corso));

        return json;
    }

    private String visualizzaRipetizioni(String ruolo) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = ruolo.equals("Admin") ? gson.toJson(DAO.visualizzaRipetizioni()) : gson.toJson(DAO.visualizzaRipetizioniDisponibili());

        return json;
    }

    private String attivarePrenotazione(String username, int ripetizione, int docente) {
        Gson gson;
        String json;

        gson = new Gson();
        DAO.registerDriver();
        json = gson.toJson(DAO.attivaPrenotazione(username, ripetizione, docente));

        return json;
    }

    private String onError(String type, String s) {
        return "{\"error\":{\"type\":\"" + type + "\",\"message\":\"" + s + "\"}}";
    }
}