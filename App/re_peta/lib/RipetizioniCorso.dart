import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:http/http.dart' as http;

import 'RipetizioniDocenti.dart';
import 'colors.dart';
import 'components/Background.dart';
import 'components/ListItem.dart';
import 'model/ripetizione.dart';
import 'model/sharedPref.dart';

class RipetizioniCorso extends StatefulWidget {
  const RipetizioniCorso({super.key, required this.corso});

  final String corso;

  @override
  RipetizioniCorsoState createState() => RipetizioniCorsoState();
}

class RipetizioniCorsoState extends State<RipetizioniCorso> {
  List<Ripetizione> ripetizioni = [];

  erCoatto() async {
    SharedPref sp = SharedPref();
    var js = jsonDecode(await sp.read("user"));

    var queryParameters = {
      'action': 'visualizzaRipetizioni',
      'session': js['session'],
      'corso': widget.corso
    };

    var uri = Uri.parse('http://localhost:8080/Re-peta/Ripetizioni');
    var response = await http.post(uri, body: queryParameters);

    // Tutte le ripetizioni di tutti i corsi
    // debugPrint(response.body);

    List johnson = jsonDecode(response.body) as List;
    List<Ripetizione> repetas =
        johnson.map((ripJohn) => Ripetizione.fromJson(ripJohn)).toList();

    List<Ripetizione> defRip = <Ripetizione>[];
    for (var rip in repetas) {
      if (rip.corso.titolo == widget.corso) {
        defRip.add(rip);
      }
    }

    debugPrint(Ripetizione.printRipetizioni(defRip));

    setState(() {
      ripetizioni = defRip;
    });
  }

  @override
  void initState() {
    erCoatto();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Background(
        corso: widget.corso,
        oldPage: "Ripetizioni",
        child: RipetizioniInfo(ripetizioni: ripetizioni, titolo: widget.corso));
  }
}

class RipetizioniInfo extends StatelessWidget {
  const RipetizioniInfo(
      {super.key, required this.ripetizioni, required this.titolo});

  final String titolo;
  final List<Ripetizione> ripetizioni;

  @override
  Widget build(BuildContext context) {
    return ListView(children: <Widget>[
      ListView.builder(
          padding: const EdgeInsets.all(8),
          physics: const ScrollPhysics(),
          shrinkWrap: true,
          itemCount: ripetizioni.length,
          itemBuilder: (BuildContext context, int index) {
            return InkWell(
              highlightColor: Colors.transparent,
              focusColor: Colors.transparent,
              hoverColor: Colors.transparent,
              splashColor: Colors.transparent,
                onTap: () => Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(
                        builder: (context) => RipetizioniDocenti(
                            ripetizione: ripetizioni[index]))),
                child: ListItem(
                    item:
                        "${ripetizioni[index].giorno}, ${ripetizioni[index].oraInizio}:00 - ${ripetizioni[index].oraFine}:00"));
          }),
      // Footer

      Container(
        height: 100,
        color: Colors.transparent,
        child: Center(
          child: // Footer
              Text(
            "Re-peta",
            style: GoogleFonts.oleoScript(
              fontSize: 50,
              color: lightColor,
            ),
          ),
        ),
      ),
    ]);
  }
}
