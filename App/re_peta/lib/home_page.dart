import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:google_fonts/google_fonts.dart';
import 'package:re_peta/model/sharedPref.dart';
import 'colors.dart';
import 'components/ProfiloUtente.dart';
import 'model/user.dart';
import 'prenotazioni.dart';
import 'ripetizioni.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key, required this.pagina});

  final String pagina;

  @override
  HomeScreenState createState() => HomeScreenState(pagina: pagina);
}

class HomeScreenState extends State<HomePage> {
  HomeScreenState({required this.pagina});
  User user = User(
      username: '',
      password: '',
      nome: '',
      cognome: '',
      session: '',
      ruolo: '');
  late SharedPref sp;

  final List<Widget> _screens = [
    // Content for Home tab
    Container(
      margin: const EdgeInsets.only(top: 0),
      alignment: Alignment.center,
      color: Colors.white,
      child: const Ripetizioni(),
    ),
    // Content for Feed tab
    Container(
      margin: const EdgeInsets.only(top: 0),
      alignment: Alignment.center,
      color: Colors.white,
      child: const Prenotazioni(),
    ),
  ];
  String pagina;
  final globalKey = GlobalKey<ScaffoldState>();

  Future<bool> _onWillPop() async {
    return (await showDialog(
          context: context,
          builder: (context) => AlertDialog(
            title: const Text('Vuoi davvero chiudere?'),
            content: const Text('Stai per chiudere Re-peta, ci mancherai...'),
            actions: <Widget>[
              TextButton(
                onPressed: () =>
                    Navigator.of(context).pop(false), //<-- SEE HERE
                child: const Text('No'),
              ),
              TextButton(
                onPressed: () =>
                    Navigator.of(context).pop(true), // <-- SEE HERE
                child: const Text('Sì'),
              ),
            ],
          ),
        )) ??
        false;
  }

  loadSharedPrefs() async {
    sp = SharedPref();
    try {
      var johnson = jsonDecode(await sp.read("user"));
      User subumano = User(
          username: johnson['username'],
          password: johnson['password'],
          ruolo: johnson['ruolo'],
          nome: johnson['nome'],
          cognome: johnson['cognome'],
          session: johnson['session']);
      setState(() {
        user = subumano;
      });
    } catch (Excepetion) {
      debugPrint(Excepetion.toString());
    }
  }

  @override
  void initState() {
    loadSharedPrefs();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    int selectedIndex = (pagina == "Ripetizioni") ? 0 : 1;
    final Size size = MediaQuery.of(context).size;

    return WillPopScope(
      onWillPop: _onWillPop,
      child: Scaffold(
        key: globalKey,
        endDrawer: ProfiloUtente(
            nome: user.nome,
            cognome: user.cognome,
            username: user.username,
            ruolo: user.ruolo,
            scaffoldKey: globalKey),
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
                Expanded(
                  child: Text(
                    "Re-peta",
                    style: GoogleFonts.oleoScript(
                      fontSize: 50,
                      color: blanco,
                    ),
                  ),
                ),
              ],
            ),
            actions: <Widget>[
              Padding(
                padding: const EdgeInsets.only(right: 20),
                child: InkWell(
                  onTap: () => globalKey.currentState?.openEndDrawer(),
                  child: const CircleAvatar(
                    radius: 30,
                    backgroundImage: NetworkImage(
                      'https://source.unsplash.com/150x150/?portrait',
                    ),
                  ),
                ),
              ),
            ]),
        bottomNavigationBar: SizedBox(
          height: size.height * 0.10,
          child: BottomNavigationBar(
              backgroundColor: snowpiercer,
              currentIndex: selectedIndex,
              unselectedItemColor: blanco,
              selectedItemColor: blanco,
              selectedFontSize: 20,
              unselectedFontSize: 12,
              enableFeedback: true,
              // called when one tab is selected
              onTap: (int index) {
                setState(() {
                  pagina = (index == 0) ? "Ripetizioni" : "Prenotazioni";
                  selectedIndex = index;
                });
              },
              // bottom tab items
              items: const [
                BottomNavigationBarItem(
                    icon: Icon(Icons.book_online, color: blanco),
                    label: 'Ripetizioni'),
                BottomNavigationBarItem(
                    icon: Icon(Icons.bookmarks, color: blanco),
                    label: 'Prenotazioni'),
              ]),
        ),
        body: Row(
          children: [Expanded(child: _screens[selectedIndex])],
        ),
      ),
    );
  }
}
