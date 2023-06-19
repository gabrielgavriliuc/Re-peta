import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';

import 'login_page.dart';
import 'model/prenotazione.dart';

import 'PrenotazioneCorso.dart';
import 'colors.dart';
import 'model/sharedPref.dart';

import 'package:http/http.dart' as http;

class Prenotazioni extends StatefulWidget {
  const Prenotazioni({super.key});

  @override
  PrenotazioniState createState() => PrenotazioniState();
}

class PrenotazioniState extends State<StatefulWidget> {
  List<Prenotazione> entries = <Prenotazione>[];

  void showBanner(BuildContext context, String message) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
        duration: const Duration(seconds: 5),
        padding:
            const EdgeInsets.only(right: 16, left: 16, top: 10, bottom: 10),
        backgroundColor: lightColor,
        content: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            const Icon(Icons.error_outline_rounded, color: tomato),
            Text(message,
                style: GoogleFonts.poppins(
                    fontSize: 16, fontWeight: FontWeight.w400, color: blackCat),
                softWrap: true,
                maxLines: 2),
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
                child: Text('ACCEDI',
                    style: GoogleFonts.poppins(
                        fontSize: 16,
                        fontWeight: FontWeight.w600,
                        color: blackCat)),
              ),
            ),
          ],
        )));
  }

  getPrenotazioni() async {
    SharedPref sp = SharedPref();
    var js = jsonDecode(await sp.read("user"));

    var queryParameters = {
      'action': 'visualizzaPrenotazioni',
      'session': js['session']
    };

    var uri = Uri.parse('http://localhost:8080/Re-peta/Prenotazioni');
    var response = await http.post(uri, body: queryParameters);

    var re = jsonDecode(response.body);
    if (re[0] == null) {
      debugPrint(re['error']['message']);
      // ignore: use_build_context_synchronously
      showBanner(context, re['error']['message']);
    } else {
      showBanner(context, "Sessione scaduta come er latte 'n culo");
      List johnson = jsonDecode(response.body) as List;
      List<Prenotazione> prenotationi =
          johnson.map((ripJohn) => Prenotazione.fromJson(ripJohn)).toList();

      prenotationi.sort(((a, b) =>
          a.ripetizione.corso.titolo.compareTo(b.ripetizione.corso.titolo)));

      setState(() {
        entries = prenotationi;
      });
    }
  }

  @override
  void initState() {
    getPrenotazioni();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      padding: const EdgeInsets.all(8),
      itemCount: entries.length,
      itemBuilder: (BuildContext context, int index) {
        return Container(
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(80),
            color: blanco,
            boxShadow: const [
              BoxShadow(
                  color: blackCat,
                  spreadRadius: -10,
                  blurRadius: 12,
                  offset: Offset(0, 4),
                  blurStyle: BlurStyle.normal),
            ],
          ),
          height: 70,
          margin: const EdgeInsets.only(top: 25, left: 20, right: 20),
          child: InkWell(
            onTap: () => Navigator.pushReplacement(
                context,
                MaterialPageRoute(
                    builder: (context) =>
                        PrenotazioneCorso(prenotazione: entries[index]))),
            child: Container(
              alignment: Alignment.centerLeft,
              margin: const EdgeInsets.only(left: 20),
              child: Row(
                children: [
                  Expanded(
                    child: Text(
                      entries[index].ripetizione.corso.titolo,
                      textAlign: TextAlign.left,
                      style: const TextStyle(
                          fontWeight: FontWeight.bold,
                          color: blueocean,
                          fontSize: 16),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.only(right: 20),
                    child: Text(
                      entries[index].stato,
                      textAlign: TextAlign.left,
                      style: TextStyle(
                          fontWeight: FontWeight.bold,
                          color: ((entries[index].stato == "Attivata")
                              ? goldenSand
                              : (entries[index].stato == "Effettuata")
                                  ? leaf
                                  : tomato),
                          fontSize: 16),
                    ),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}
