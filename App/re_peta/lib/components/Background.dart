import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:re_peta/RipetizioniCorso.dart';
import 'package:re_peta/RipetizioniDocenti.dart';
import 'package:re_peta/home_page.dart';
import 'package:re_peta/model/corso.dart';
import 'package:re_peta/model/docente.dart';
import '../colors.dart';
import '../model/ripetizione.dart';

class Background extends StatelessWidget {
  const Background(
      {super.key,
      required this.corso,
      this.ripetizione,
      required this.child,
      required this.oldPage});

  final Ripetizione? ripetizione;
  final Widget child;
  final String oldPage;
  final String corso;

  @override
  Widget build(BuildContext context) {
    final size = MediaQuery.of(context).size;
    final Ripetizione repeta = ripetizione ??
        Ripetizione(0, Corso('', 0, false), <Docente>[], '', '', '', 0);

    return WillPopScope(
      onWillPop: () {
        Navigator.pushReplacement(
            context,
            MaterialPageRoute(
                builder: (context) => (oldPage == "RipetizioniCorso")
                    ? RipetizioniCorso(
                        corso: corso,
                      )
                    : (oldPage == "Ripetizioni")
                        ? const HomePage(pagina: "Ripetizioni")
                        : (oldPage == "RipetizioniDocenti")
                            ? RipetizioniDocenti(
                                ripetizione: repeta,
                              )
                            : const HomePage(pagina: "Prenotazioni")));
        return Future<bool>.value(false);
      },
      child: Scaffold(
        appBar: AppBar(
          toolbarHeight: size.height * 0.125,
          backgroundColor: snowpiercer,
          shape: const RoundedRectangleBorder(
            borderRadius: BorderRadius.vertical(
              bottom: Radius.circular(40),
            ),
          ),
          title: Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              InkWell(
                  onTap: () => Navigator.pushReplacement(
                      context,
                      MaterialPageRoute(
                          builder: (context) => (oldPage == "RipetizioniCorso")
                              ? RipetizioniCorso(corso: corso)
                              : (oldPage == "Ripetizioni")
                                  ? const HomePage(pagina: "Ripetizioni")
                                  : (oldPage == "RipetizioniDocenti")
                                      ? RipetizioniDocenti(ripetizione: repeta)
                                      : const HomePage(
                                          pagina: "Prenotazioni"))),
                  child: const Icon(Icons.arrow_back_rounded,
                      color: blanco, size: 38)),
              Expanded(
                child: Container(
                  margin: const EdgeInsets.only(top: 0, left: 10),
                  child: Text(
                    corso,
                    style: GoogleFonts.poppins(
                        fontSize: 25,
                        color: blanco,
                        fontWeight: FontWeight.bold),
                    maxLines: 2,
                    overflow: TextOverflow.ellipsis,
                  ),
                ),
              ),
            ],
          ),
        ),
        body: child,
      ),
    );
  }
}
