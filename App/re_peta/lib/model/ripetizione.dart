import 'dart:convert';
import 'corso.dart';
import 'docente.dart';

class Ripetizione {
  int id;
  Corso corso;
  List<Docente> docenti;
  String giorno;
  String oraInizio;
  String oraFine;
  int stato;

  Ripetizione(this.id, this.corso, this.docenti, this.giorno, this.oraInizio,
      this.oraFine, this.stato);

  factory Ripetizione.fromJson(dynamic js) {
    var docentiList = js['docenti'] as List;
    List<Docente> docBrowns =
        docentiList.map((docJson) => Docente.fromJson(docJson)).toList();

    return Ripetizione(
        js['id'] as int,
        Corso.fromJson(js['corso']),
        docBrowns,
        js['giorno'] as String,
        js['oraInizio'] as String,
        js['oraFine'] as String,
        js['stato'] as int);
  }

  Map<String, dynamic> toJson() => {
        'id': id,
        'corso': corso,
        'docenti': jsonEncode(docenti),
        'giorno': giorno,
        'oraInizio': oraInizio,
        'oraFine': oraFine,
        'stato': stato
      };

  @override
  String toString() {
    return "Ripetizione {id: $id, corso: ${corso.titolo}, docenti: {${Docente.printDocenti(docenti)}}, giorno: $giorno, ore: $oraInizio-$oraFine, stato: $stato}";
  }

  static String printRipetizioni(List<Ripetizione> rips) {
    String result = 'Ripetizioni: {';
    for (var ripetizione in rips) {
      result += "${ripetizione.toString()}\n";
    }
    return "$result \n}";
  }
}
