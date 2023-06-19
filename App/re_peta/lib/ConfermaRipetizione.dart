import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'colors.dart';
import 'components/Background.dart';
import 'package:http/http.dart' as http;
import 'home_page.dart';
import 'login_page.dart';
import 'model/docente.dart';
import 'model/ripetizione.dart';
import 'model/sharedPref.dart';

class ConfermaRipetizione extends StatelessWidget {
  const ConfermaRipetizione(
      {super.key, required this.ripetizione, required this.docente});

  final Ripetizione ripetizione;
  final Docente docente;

  @override
  Widget build(BuildContext context) {
    return Background(
        corso: ripetizione.corso.titolo,
        oldPage: "RipetizioniDocenti",
        ripetizione: ripetizione,
        child: Riepilogo(
            data:
                "${ripetizione.giorno}, ${ripetizione.oraInizio}:00 - ${ripetizione.oraFine}:00",
            docente: docente,
            corso: ripetizione.corso.titolo,
            id: ripetizione.id));
  }
}

class Riepilogo extends StatelessWidget {
  const Riepilogo(
      {super.key,
      required this.data,
      required this.docente,
      required this.corso,
      required this.id});

  final String data;
  final Docente docente;
  final String corso;
  final int id;

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;

    return ListView(
      children: [
        Column(
          mainAxisAlignment: MainAxisAlignment.start,
          children: [
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
                    Container(
                      margin: const EdgeInsets.only(top: 20),
                      child: Text("Riepilogo",
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
                            child: Text(corso,
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
                            child: Text(data,
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
                              "${docente.nome} ${docente.cognome}",
                              style: GoogleFonts.poppins(
                                  color: blueocean,
                                  fontSize: 20,
                                  fontWeight: FontWeight.normal),
                            ),
                          )
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
                                color: tomato,
                              ),
                              width: size.width * 0.65,
                              height: 60,
                              margin: const EdgeInsets.only(top: 0),
                              child: TextButton(
                                onPressed: () => Navigator.pushReplacement(
                                    context,
                                    MaterialPageRoute(
                                        builder: (context) => const HomePage(
                                            pagina: "Ripetizioni"))),
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
                                color: leaf,
                              ),
                              width: size.width * 0.65,
                              height: 60,
                              margin:
                                  const EdgeInsets.only(top: 20, bottom: 20),
                              child: TextButton(
                                onPressed: () => erCoatto(context),
                                child: const Text(
                                  "Conferma",
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

  void erCoatto(BuildContext context) async {
    SharedPref sp = SharedPref();
    var js = jsonDecode(await sp.read("user"));

    var queryParameters = {
      'action': "attivarePrenotazione",
      'session': js['session'],
      'ripetizione': id.toString(),
      'docente': docente.id.toString(),
    };

    var uri = Uri.parse('http://localhost:8080/Re-peta/Ripetizioni');
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
}
