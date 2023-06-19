class Docente {
  int id;
  String nome;
  String cognome;
  bool stato;

  Docente(this.id, this.nome, this.cognome, this.stato);

  Docente.fromJson(Map<String, dynamic> json)
      : id = json['id'],
        nome = json['nome'],
        cognome = json['cognome'],
        stato = json['stato'];

  Map<String, dynamic> toJson() =>
      {'id': id, 'nome': nome, 'cognome': cognome, 'stato': stato};

  static String printDocenti(List<Docente> docenti) {
    String result = "";
    for (var docente in docenti) {
          result += "nome: ${docente.nome} ${docente.cognome}, ";
        }

    return result;
  }
}
