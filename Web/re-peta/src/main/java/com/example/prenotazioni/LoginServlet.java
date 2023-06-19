package com.example.prenotazioni;

import com.example.prenotazioni.DAO.*;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {
    private DAO dao = null;
    private final Utente guest = new Utente("Guest Pequeno", "", "", "", "Guest");

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
        System.out.println("LOGIN: SONO IN INIT");
        ServletContext ctx = conf.getServletContext();
        String url = ctx.getInitParameter("DB-URL");
        String database = ctx.getInitParameter("database");
        String user = ctx.getInitParameter("user");
        String password = ctx.getInitParameter("password");

        dao = new DAO(url, database, user, password);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean logout = false;
        String action, js = "";
        PrintWriter out;

        action = request.getParameter("action");

        switch (action) {
            case "login":
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                if (username != null && password != null)
                    js = this.doLogIn(request, username, password);
                else
                    js = onError("Errore", "Controllare i parametri e riprovare");
                break;

            case "loginGuest":
                js = this.doLogInGuest(request);
                break;

            case "register":
                String nome = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                username = request.getParameter("username");
                password = request.getParameter("password");

                if (nome != null && cognome != null && username != null && password != null)
                    js = this.doRegister(request, nome, cognome, username, password);
                else
                    js = onError("Errore", "Controllare i parametri e riprovare");
                break;

            case "getSession":
                js = this.getSession(request);
                break;

            case "logout":
            default:
                js = this.doLogOut(request);
        }

        response.setContentType("application/json");
        out = response.getWriter();
        out.print(js);
        System.out.println("LoginServlet: " + js);
        out.close();
    }

    private String doLogIn(HttpServletRequest request, String username, String password) {
        HttpSession s;
        Utente account;
        String json;
        Gson gson;

        gson = new Gson();
        DAO.registerDriver();
        account = DAO.login(username, password);

        if (account != null) {
            s = request.getSession(true);

            if (s != null) {
                s.setAttribute("username", account.getUsername());
                s.setAttribute("password", account.getPassword());
                s.setAttribute("ruolo", account.getRuolo());

                json = gson.toJson(account);
                System.out.println("LOGIN: Session ID: " + s.getId() + " Ruolo impostato a: " + s.getAttribute("ruolo"));

                json = json.substring(0, json.length() - 1) + ", \"sessione\": \"" + s.getId() + "\"}";
            } else
                json = onError("Errore", "Impossibile creare la sessione");
        } else
            json = onError("Errore", "Utente non riconosciuto");

        return json;
    }

    private String doLogInGuest(HttpServletRequest request) {
        HttpSession s;
        Utente account;
        String json;
        Gson gson;

        gson = new Gson();
        DAO.registerDriver();

        s = request.getSession(true);

        if (s != null) {
            account = this.guest;
            s.setAttribute("username", account.getUsername());
            s.setAttribute("ruolo", account.getRuolo());

            json = gson.toJson(account);
            System.out.println("LOGIN: Session ID: " + s.getId() + " Ruolo impostato a: " + s.getAttribute("ruolo"));
        } else
            json = onError("Errore", "Impossibile creare la sessione");

        return json;
    }

    private String doRegister(HttpServletRequest request, String nome, String cognome, String username, String password) {
        HttpSession s;
        Utente account;
        String json;
        Gson gson;

        gson = new Gson();
        DAO.registerDriver();
        if (DAO.inserisciUtente(username, password, nome, cognome, "Cliente"))
            account = DAO.login(username, password);
        else account = null;

        if (account != null) {
            s = request.getSession(true);

            if (s != null) {
                s.setAttribute("username", account.getUsername());
                s.setAttribute("password", account.getPassword());
                s.setAttribute("ruolo", account.getRuolo());

                json = gson.toJson(account);
                System.out.println("LOGIN: Session ID: " + s.getId() + " Ruolo impostato a: " + s.getAttribute("ruolo"));
            } else
                json = onError("Errore", "Impossibile creare la sessione");
        } else
            json = onError("Errore", "Impossibile creare l'utente");

        return json;
    }

    private String getSession(HttpServletRequest request) {
        HttpSession s;
        Utente account;
        String username, password, ruolo, json;
        Gson gson;

        gson = new Gson();

        s = request.getSession(false);
        ruolo = (String) s.getAttribute("ruolo");
        username = (String) s.getAttribute("username");

        account = this.guest;

        if (ruolo.equals("Guest")) {
            json = gson.toJson(account);
        } else {
            password = (String) s.getAttribute("password");

            if (s != null && username != null && password != null) {
                DAO.registerDriver();
                account = DAO.login(username, password);
                if (account != null)
                    json = gson.toJson(account);
                else
                    json = onError("Errore", "Account inesistente");
            } else
                json = onError("Errore", "Sessione non riconosciuta");
        }
        String facile = json.substring(0, json.length() - 1) + ", \"sessione\": \"" + s.getId() + "\"}";

        return facile;
    }

    private String doLogOut(HttpServletRequest request) {
        HttpSession s = request.getSession(false);

        if (s != null)
            s.invalidate();

        return "{\"session\":{\"type\":\"" + "session" + "\",\"message\":\"" + "sessione invalidata" + "\"}}";
    }

    private String onError(String type, String s) {
        return "{\"error\":{\"type\":\"" + type + "\",\"message\":\"" + s + "\"}}";
    }
}