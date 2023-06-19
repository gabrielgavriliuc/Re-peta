import 'package:flutter/material.dart';
import '../home_page.dart';
import '../login_page.dart';
import '../colors.dart';
import '../prenotazioni.dart';
import '../ripetizioni.dart';

class RoundedButton extends StatelessWidget {
  const RoundedButton(
      {Key? key,
      required this.text,
      required this.margin,
      required this.route,
      required this.color})
      : super(key: key);

  final String text;
  final double margin;
  final String route;
  final Color color;

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(80),
        color: color,
      ),
      width: size.width * 0.65,
      height: 60,
      margin: EdgeInsets.only(top: margin),
      child: TextButton(
        onPressed: () => Navigator.pushReplacement(
            context,
            MaterialPageRoute(
                builder: (context) => (route == "HomePage") ? const HomePage(pagina: "Ripetizioni")
                    : ((route == "Prenotazioni") ? const HomePage(pagina: "Prenotazioni")
                        : ((route == "Ripetizioni") ? const HomePage(pagina: "Ripetizioni")
                            : const LoginPage())))),
        child: Text(
          text,
          style: const TextStyle(
              fontSize: 20, fontWeight: FontWeight.bold, color: blanco),
        ),
      ),
    );
  }
}
