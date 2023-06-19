import 'dart:convert';

import 'user.dart';
import 'docente.dart';
import 'ripetizione.dart';

class Prenotazione {
  Ripetizione ripetizione;
  Docente docente;
  User utente;
  String stato;

  Prenotazione(this.ripetizione, this.docente, this.utente, this.stato);

  factory Prenotazione.fromJson(dynamic js) {
    return Prenotazione(
        Ripetizione.fromJson(js['ripetizione']),
        Docente.fromJson(js['docente']),
        User.fromJson(js['utente']),
        js['stato'] as String);
  }

  Map<String, dynamic> toJson() => {
        'ripetizione': jsonEncode(ripetizione),
        'docente': jsonEncode(docente),
        'user': jsonEncode(utente),
        'stato': stato
      };

  @override
  String toString() {
    return "Prenotazione: corso = ${ripetizione.corso.titolo}\tdocente = ${docente.nome} ${docente.cognome}\tutente = ${utente.username}\tstato = $stato}\n";
  }

  static String printPrenotazioni(List<Prenotazione> prens) {
    String result = 'Prenotazioni: {';
    for (var prenotazione in prens) {
      result += "${prenotazione.toString()}\n";
    }
    return "$result \n}";
  }
}
