import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:re_peta/home_page.dart';

import 'colors.dart';
import 'components/Background.dart';
import 'package:http/http.dart' as http;
import 'login_page.dart';
import 'model/prenotazione.dart';
import 'model/sharedPref.dart';

class PrenotazioneCorso extends StatelessWidget {
  const PrenotazioneCorso({super.key, required this.prenotazione});

  final Prenotazione prenotazione;

  @override
  Widget build(BuildContext context) {
    return Background(
        corso: prenotazione.ripetizione.corso.titolo,
        oldPage: 'Prenotazioni',
        child: PrenotazioneInfo(prenotazione: prenotazione));
  }
}

class PrenotazioneInfo extends StatelessWidget {
  const PrenotazioneInfo({super.key, required this.prenotazione});

  final Prenotazione prenotazione;

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;

    return ListView(
      children: [
        Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
            // Box
            Expanded(
              flex: 0,
              child: Container(
                width: double.infinity,
                height: size.height * 0.65,
                constraints: const BoxConstraints(
                  minHeight: 500,
                  maxHeight: 700,
                ),
                margin: const EdgeInsets.all(20),
                decoration: BoxDecoration(
                  borderRadius: BorderRadius.circular(80),
                  color: blanco,
                  boxShadow: const [
                    BoxShadow(
                        color: blackCat,
                        spreadRadius: -8,
                        blurRadius: 12,
                        offset: Offset(0, 4),
                        blurStyle: BlurStyle.normal),
                  ],
                ),
                child: Column(
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: [
                    // Titolo
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: Text("Info prenotazione",
                          style: GoogleFonts.poppins(
                              color: blackCat,
                              fontSize: 24,
                              fontWeight: FontWeight.bold)),
                    ),
                    // Corso
                    Container(
                      margin: const EdgeInsets.only(left: 30, top: 20),
                      child: Row(
                        mainAxisSize: MainAxisSize.min,
                        textDirection: TextDirection.ltr,
                        children: [
                          Text("Corso: ",
                              style: GoogleFonts.poppins(
                                  color: blackCat,
                                  fontSize: 20,
                                  fontWeight: FontWeight.normal)),
                          Expanded(
                            child: Text(prenotazione.ripetizione.corso.titolo,
                                style: GoogleFonts.poppins(
                                    color: blueocean,
                                    fontSize: 20,
                                    fontWeight: FontWeight.normal)),
                          ),
                        ],
                      ),
                    ),
                    // Data
                    Container(
                      margin: const EdgeInsets.only(left: 30, top: 20),
                      child: Row(
                        mainAxisSize: MainAxisSize.min,
                        textDirection: TextDirection.ltr,
                        children: [
                          Text("Data: ",
                              style: GoogleFonts.poppins(
                                  color: blackCat,
                                  fontSize: 20,
                                  fontWeight: FontWeight.normal)),
                          Expanded(
                            child: Text(
                                "${prenotazione.ripetizione.giorno}, ${prenotazione.ripetizione.oraInizio}:00 ${prenotazione.ripetizione.oraFine}:00",
                                style: GoogleFonts.poppins(
                                    color: blueocean,
                                    fontSize: 20,
                                    fontWeight: FontWeight.normal)),
                          ),
                        ],
                      ),
                    ),
                    // Docente
                    Container(
                      margin: const EdgeInsets.only(left: 30, top: 20),
                      child: Row(
                        mainAxisSize: MainAxisSize.min,
                        textDirection: TextDirection.ltr,
                        children: [
                          Text("Docente: ",
                              style: GoogleFonts.poppins(
                                  color: blackCat,
                                  fontSize: 20,
                                  fontWeight: FontWeight.normal)),
                          Expanded(
                            child: Text(
                              "${prenotazione.docente.nome} ${prenotazione.docente.cognome}",
                              style: GoogleFonts.poppins(
                                  color: blueocean,
                                  fontSize: 20,
                                  fontWeight: FontWeight.normal),
                            ),
                          )
                        ],
                      ),
                    ),
                    // Stato
                    Container(
                      margin: const EdgeInsets.only(left: 30, top: 20),
                      child: Row(
                        mainAxisSize: MainAxisSize.min,
                        textDirection: TextDirection.ltr,
                        children: [
                          Text("Stato: ",
                              style: GoogleFonts.poppins(
                                  color: blackCat,
                                  fontSize: 20,
                                  fontWeight: FontWeight.normal)),
                          Expanded(
                            child: Text(prenotazione.stato,
                                style: GoogleFonts.poppins(
                                    color: ((prenotazione.stato == "Attivata")
                                        ? goldenSand
                                        : (prenotazione.stato == "Effettuata")
                                            ? leaf
                                            : tomato),
                                    fontSize: 20,
                                    fontWeight: FontWeight.normal)),
                          ),
                        ],
                      ),
                    ),
                    Expanded(
                      child: Column(
                          mainAxisAlignment: MainAxisAlignment.end,
                          children: [
                            // Annulla
                            Container(
                              decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(80),
                                color: (prenotazione.stato == "Attivata")
                                    ? tomato
                                    : greyjoy,
                              ),
                              width: size.width * 0.65,
                              height: 60,
                              margin: const EdgeInsets.only(top: 0),
                              child: TextButton(
                                onPressed: () =>
                                    erCoatto(context, "disdirePrenotazione"),
                                child: const Text(
                                  "Cancella",
                                  style: TextStyle(
                                      fontSize: 20,
                                      fontWeight: FontWeight.bold,
                                      color: blanco),
                                ),
                              ),
                            ),

                            // Conferma
                            Container(
                              decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(80),
                                color: (prenotazione.stato == "Attivata")
                                    ? leaf
                                    : greyjoy,
                              ),
                              width: size.width * 0.65,
                              height: 60,
                              margin:
                                  const EdgeInsets.only(top: 20, bottom: 20),
                              child: TextButton(
                                onPressed: () =>
                                    erCoatto(context, "effettuarePrenotazione"),
                                child: const Text(
                                  "Effettuata",
                                  style: TextStyle(
                                      fontSize: 20,
                                      fontWeight: FontWeight.bold,
                                      color: blanco),
                                ),
                              ),
                            ),
                          ]),
                    ),
                  ],
                ),
              ),
            ),
            Container(
              height: size.width * 0.2,
              margin: const EdgeInsets.only(top: 50, bottom: 12),
              child: Text(
                "Re-peta",
                style: GoogleFonts.oleoScript(
                  fontSize: size.width * 0.15,
                  color: lightColor,
                ),
              ),
            ),
          ],
        ),
      ],
    );
  }

  void showBanner(BuildContext context, String message) {
    ScaffoldMessenger.of(context).showMaterialBanner(MaterialBanner(
        padding: const EdgeInsets.all(20),
        backgroundColor: lightColor.withOpacity(0.2),
        leading: const Icon(Icons.error_outline_rounded, color: tomato),
        content: Text(message,
            style: GoogleFonts.poppins(
                fontSize: 20, fontWeight: FontWeight.w400, color: blackCat)),
        actions: <Widget>[
          InkWell(
            highlightColor: blueocean.withOpacity(0.3),
            focusColor: blueocean.withOpacity(0.3),
            hoverColor: blueocean.withOpacity(0.3),
            splashColor: blueocean.withOpacity(0.3),
            onTap: () => Navigator.pushReplacement(context,
                MaterialPageRoute(builder: (context) => const LoginPage())),
            child: Container(
              padding: const EdgeInsets.all(8),
              color: blueocean.withOpacity(0.5),
              child: Text('Ho capito',
                  style: GoogleFonts.poppins(
                      fontSize: 20,
                      fontWeight: FontWeight.w600,
                      color: blackCat)),
            ),
          ),
        ]));
  }

  void erCoatto(BuildContext context, String azione) async {
    if (prenotazione.stato == "Attivata") {
      SharedPref sp = SharedPref();
      var js = jsonDecode(await sp.read("user"));

      var queryParameters = {
        'action': azione,
        'session': js['session'],
        'ripetizione': prenotazione.ripetizione.id.toString(),
        'docente': prenotazione.docente.id.toString(),
        'username': js['username']
      };

      var uri = Uri.parse('http://localhost:8080/Re-peta/Prenotazioni');
      var response = await http.post(uri, body: queryParameters);

      var re = jsonDecode(response.body);

      if (re[0] == null) {
        debugPrint(re['error']['message']);
        // ignore: use_build_context_synchronously
        showBanner(context, re['error']['message']);
      } else {
        debugPrint(response.body);

        // ignore: use_build_context_synchronously
        Navigator.pushReplacement(
            context,
            MaterialPageRoute(
                builder: (context) => const HomePage(pagina: "Prenotazioni")));
      }
    }
  }
}
