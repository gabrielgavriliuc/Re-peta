import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'ConfermaRipetizione.dart';
import 'colors.dart';
import 'components/ListItem.dart';
import 'components/Background.dart';
import 'model/ripetizione.dart';

class RipetizioniDocenti extends StatelessWidget {
  const RipetizioniDocenti({super.key, required this.ripetizione});

  final Ripetizione ripetizione;

  @override
  Widget build(BuildContext context) {
    return Background(
        oldPage: "RipetizioniCorso",
        corso: ripetizione.corso.titolo,
        child: RipetizioniInfo(ripetizione: ripetizione));
  }
}

class RipetizioniInfo extends StatelessWidget {
  const RipetizioniInfo({super.key, required this.ripetizione});

  final Ripetizione ripetizione;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Container(
          margin: const EdgeInsets.only(top: 20),
          child: Text(
              "${ripetizione.giorno}, ${ripetizione.oraInizio}:00 - ${ripetizione.oraFine}:00",
              style: GoogleFonts.poppins(
                  color: blackCat, fontSize: 20, fontWeight: FontWeight.bold)),
        ),
        Expanded(
          child: ListView.builder(
              padding: const EdgeInsets.all(8),
              itemCount: ripetizione.docenti.length,
              itemBuilder: (BuildContext context, int index) {
                return InkWell(
                    highlightColor: Colors.transparent,
                    focusColor: Colors.transparent,
                    hoverColor: Colors.transparent,
                    splashColor: Colors.transparent,
                    onTap: () => Navigator.pushReplacement(
                        context,
                        MaterialPageRoute(
                            builder: (context) => ConfermaRipetizione(
                                ripetizione: ripetizione,
                                docente: ripetizione.docenti[index]))),
                    child: ListItem(
                        item:
                            "${ripetizione.docenti[index].nome} ${ripetizione.docenti[index].cognome}"));
              }),
        ),
      ],
    );
  }
}
