import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:re_peta/colors.dart';
import 'RipetizioniCorso.dart';
import 'components/ListItem.dart';

import 'package:http/http.dart' as http;

import 'login_page.dart';
import 'model/ripetizione.dart';
import 'model/sharedPref.dart';

class Ripetizioni extends StatefulWidget {
  const Ripetizioni({super.key});

  @override
  State<StatefulWidget> createState() => _RipetizioniState();
}

class _RipetizioniState extends State<Ripetizioni> {
  _RipetizioniState();

  List<Ripetizione> entries = <Ripetizione>[];

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

  getRipetizioni() async {
    SharedPref sp = SharedPref();
    var js = jsonDecode(await sp.read("user"));

    var queryParameters = {
      'action': 'visualizzaRipetizioni',
      'session': js['session']
    };

    var uri = Uri.parse('http://localhost:8080/Re-peta/Ripetizioni');
    var response = await http.post(uri, body: queryParameters);

    var re = jsonDecode(response.body);

    if (re[0] == null) {
      debugPrint(re['error']['message']);
      // ignore: use_build_context_synchronously
      showBanner(context, re['error']['message']);
    } else {
      List johnson = jsonDecode(response.body) as List;
      List<Ripetizione> ripetizioni =
          johnson.map((ripJohn) => Ripetizione.fromJson(ripJohn)).toList();

      List<Ripetizione> defRip = <Ripetizione>[];
      for (var rip in ripetizioni) {
        bool seen = false;
        for (int i = 0; i < defRip.length && !seen; i++) {
          if (rip.corso.titolo == defRip[i].corso.titolo) {
            seen = true;
          }
        }
        if (!seen) {
          defRip.add(rip);
        }
      }

      defRip.sort(((a, b) => a.corso.titolo.compareTo(b.corso.titolo)));

      setState(() {
        entries = defRip;
      });
    }
  }

  @override
  void initState() {
    getRipetizioni();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      padding: const EdgeInsets.all(8),
      itemCount: entries.length,
      itemBuilder: (BuildContext context, int index) {
        return InkWell(
          onTap: () => Navigator.pushReplacement(
              context,
              MaterialPageRoute(
                  builder: (context) =>
                      RipetizioniCorso(corso: entries[index].corso.titolo))),
          child: ListItem(item: entries[index].corso.titolo),
        );
      },
    );
  }
}
