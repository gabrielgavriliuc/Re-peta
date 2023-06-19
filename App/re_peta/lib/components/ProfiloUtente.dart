import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:re_peta/colors.dart';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';
import '../login_page.dart';

class ProfiloUtente extends StatelessWidget {
  ProfiloUtente(
      {super.key,
      required this.nome,
      required this.cognome,
      required this.username,
      required this.ruolo,
      required this.scaffoldKey});

  final String username;
  final String nome;
  final String cognome;
  final String ruolo;
  final GlobalKey<ScaffoldState> scaffoldKey;

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Container(
        color: snowpiercer,
        child: ListView(
          // Important: Remove any padding from the ListView.
          padding: EdgeInsets.zero,
          children: [
            Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    Container(
                      margin: const EdgeInsets.only(top: 35, left: 20),
                      child: Text("@$username",
                          style: GoogleFonts.poppins(
                              color: blanco,
                              fontSize: 20,
                              fontWeight: FontWeight.bold),
                          softWrap: true,
                          maxLines: 2),
                    ),
                    InkWell(
                      onTap: () => scaffoldKey.currentState?.closeEndDrawer(),
                      child: Container(
                        margin: const EdgeInsets.only(top: 35, right: 20),
                        child: const Icon(Icons.close_rounded,
                            color: blanco, size: 50),
                      ),
                    )
                  ],
                ),
                DatoUtente(label: "Nome", nome: nome, margin: 100),
                DatoUtente(label: "Cognome", nome: cognome, margin: 50),
                DatoUtente(label: "Ruolo", nome: ruolo, margin: 50),
                Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(80),
                    color: tomato,
                  ),
                  width: double.infinity,
                  height: 50,
                  margin: const EdgeInsets.only(top: 100, right: 20, left: 20),
                  child: TextButton(
                    onPressed: () => logout(context),
                    child: const Text(
                      "Esci",
                      style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                          color: blanco),
                    ),
                  ),
                ), // Text Copyright
                Expanded(
                  flex: 0,
                  child: Container(
                    alignment: AlignmentDirectional.bottomCenter,
                    width: double.infinity,
                    margin: const EdgeInsets.only(top: 50, bottom: 12),
                    child: const Text(
                      "Copyright © 2023 Re-peta, All Rights Reserved",
                      style: TextStyle(
                          fontSize: 12,
                          color: blanco,
                          fontWeight: FontWeight.w400),
                    ),
                  ),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }

  void logout(BuildContext context) async {
    var queryParameters = {
      'action': 'logout',
    };

    var uri = Uri.parse('http://localhost:8080/Re-peta/Login');
    var response = await http.post(uri, body: queryParameters);
    debugPrint(response.body);

    SharedPreferences preferences = await SharedPreferences.getInstance();
    await preferences.clear();

    // ignore: use_build_context_synchronously
    Navigator.pushReplacement(
        context, MaterialPageRoute(builder: (context) => const LoginPage()));
  }
}

class DatoUtente extends StatelessWidget {
  const DatoUtente(
      {super.key,
      required this.nome,
      required this.label,
      required this.margin});

  final String label;
  final String nome;
  final double margin;

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        // Label
        Container(
          alignment: Alignment.centerLeft,
          margin: EdgeInsets.only(top: margin, left: 20, right: 20),
          child: Text(
            label,
            style: GoogleFonts.poppins(
                color: blanco, fontSize: 20, fontWeight: FontWeight.bold),
            softWrap: true,
            maxLines: 2,
          ),
        ),

        // Nome
        Container(
          width: double.infinity,
          height: 50,
          margin: const EdgeInsets.only(top: 10, left: 20, right: 20),
          decoration: const BoxDecoration(
            color: lightColor,
            borderRadius: BorderRadius.only(
                topLeft: Radius.circular(8),
                topRight: Radius.circular(25),
                bottomLeft: Radius.circular(25),
                bottomRight: Radius.circular(8)),
          ),
          child: Container(
            margin: const EdgeInsets.only(top: 10, left: 20, right: 20),
            child: Text(
              nome,
              style: GoogleFonts.poppins(
                  color: blackCat, fontSize: 20, fontWeight: FontWeight.bold),
              softWrap: true,
              maxLines: 2,
            ),
          ),
        )
      ],
    );
  }
}
