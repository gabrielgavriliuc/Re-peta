import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'colors.dart';
import 'components/TextFieldContainer.dart';
import 'package:http/http.dart' as http;

import 'home_page.dart';
import 'model/sharedPref.dart';
import 'model/user.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<StatefulWidget> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {
  final textFieldControllerUsername = TextEditingController();
  final textFieldControllerPassword = TextEditingController();

  @override
  void dispose() {
    textFieldControllerUsername.dispose();
    textFieldControllerPassword.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final Size size = MediaQuery.of(context).size;

    return MaterialApp(
      title: 'Re-peta: Login',
      theme: ThemeData(
        textTheme: GoogleFonts.poppinsTextTheme(
          Theme.of(context).textTheme,
        ),
      ),
      home: Scaffold(
        body: ListView(
          padding: EdgeInsets.zero,
          children: [
            Column(
              mainAxisAlignment: MainAxisAlignment.start,
              children: [
                // Image logo
                Stack(
                  children: <Widget>[
                    ClipRRect(
                      borderRadius: const BorderRadius.only(
                        bottomLeft: Radius.circular(80),
                        bottomRight: Radius.circular(80),
                      ),
                      child: Image(
                          image: const AssetImage("assets/images/logo.png"),
                          width: size.width),
                    ),
                    Positioned(
                      top: size.width * 0.78,
                      left: 20,
                      right: 20,
                      child: Container(
                        width: double.infinity,
                        color: Colors.transparent,
                        child: Align(
                          alignment: AlignmentDirectional.center,
                          child: Text(
                            "Re-peta",
                            style: GoogleFonts.oleoScript(
                              fontSize: size.width * 0.16,
                              color: blanco,
                            ),
                            softWrap: true,
                            maxLines: 1,
                          ),
                        ),
                      ),
                    ),
                  ],
                ),

                // TextField Username
                const SizedBox(height: 20),
                Container(
                  margin: const EdgeInsets.only(top: 10),
                  child: TextFieldContainer(
                    placeholder: "Username",
                    margin: 0,
                    controller: textFieldControllerUsername,
                  ),
                ),

                // TextField Password
                const SizedBox(height: 5),
                Container(
                  margin: const EdgeInsets.only(top: 10),
                  child: TextFieldContainer(
                    placeholder: "Password",
                    margin: 0,
                    controller: textFieldControllerPassword,
                  ),
                ),

                // Text
                Expanded(
                  flex: 0,
                  child: Container(
                    height: 60,
                    alignment: AlignmentDirectional.bottomCenter,
                    child: const Text(
                      "Password dimenticata?",
                      style: TextStyle(
                          fontSize: 16,
                          color: snowpiercer,
                          fontWeight: FontWeight.w100),
                    ),
                  ),
                ),

                // Button Accedi
                Expanded(
                  flex: 0,
                  child: Container(
                    margin: const EdgeInsets.only(top: 20),
                    height: 60,
                    child: Container(
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(80),
                        color: snowpiercer,
                      ),
                      width: size.width * 0.65,
                      height: 60,
                      margin: const EdgeInsets.only(top: 0),
                      child: TextButton(
                        onPressed: () => logIn(context),
                        child: const Text(
                          "Accedi",
                          style: TextStyle(
                              fontSize: 20,
                              fontWeight: FontWeight.bold,
                              color: blanco),
                        ),
                      ),
                    ),
                  ),
                ),

                // Button Accedi Guest
                Expanded(
                  flex: 0,
                  child: Container(
                    height: 60,
                    alignment: AlignmentDirectional.bottomCenter,
                    child: const Text(
                      "Accedi come ospite",
                      style: TextStyle(
                          fontSize: 18,
                          color: snowpiercer,
                          fontWeight: FontWeight.w800),
                    ),
                  ),
                ),

                // Text Copyright
                Expanded(
                  flex: 0,
                  child: Container(
                    alignment: AlignmentDirectional.bottomCenter,
                    margin: const EdgeInsets.only(top: 50, bottom: 12),
                    child: const Text(
                      "Copyright © 2023 Re-peta, All Rights Reserved",
                      style: TextStyle(
                          fontSize: 12,
                          color: greyjoy,
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

  void logIn(BuildContext context) async {
    final username = textFieldControllerUsername.text;
    final password = textFieldControllerPassword.text;

    var queryParameters = {
      'action': 'login',
      'username': username,
      'password': password
    };

    var uri = Uri.parse('http://localhost:8080/Re-peta/Login');
    var response = await http.post(uri, body: queryParameters);

    var johnson = jsonDecode(response.body);

    if (johnson.containsKey("error")) {
      // ignore: use_build_context_synchronously
      showToast(context, johnson['error']['message']);
    } else {
      queryParameters = {
        'action': 'getSession',
      };
      response = await http.post(uri, body: queryParameters);

      johnson = jsonDecode(response.body);

      User user = User(
          username: johnson['username'],
          password: johnson['password'],
          ruolo: johnson['ruolo'],
          nome: johnson['nome'],
          cognome: johnson['cognome'],
          session: johnson['sessione']);

      debugPrint(user.toString());

      SharedPref prefs = SharedPref();
      prefs.save('user', json.encode(user));

      // ignore: use_build_context_synchronously
      Navigator.pushReplacement(
          context,
          MaterialPageRoute(
              builder: (context) => const HomePage(pagina: 'Ripetizioni')));
    }

    // queryParameters = {
    //   'action': 'logout',
    // };
    // response = await http.post(uri, body: queryParameters);
  }

  void showToast(BuildContext context, String message) {
    ScaffoldMessenger.of(context).showSnackBar(SnackBar(
      content: Text(message),
    ));
  }
}
