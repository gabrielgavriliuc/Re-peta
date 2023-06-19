import 'dart:convert';

class User {
  String username;
  String password;
  String nome;
  String cognome;
  String session;
  String ruolo;

  User(
      {required this.username,
      required this.password,
      required this.nome,
      required this.cognome,
      required this.session,
      required this.ruolo});

  @override
  String toString() {
    return "User: {username: $username, password: $password, nome: $nome, cognome: $cognome, sessione: $session}";
  }

  User.fromJson(Map<String, dynamic> json)
      : nome = json['nome'],
        cognome = json['cognome'],
        username = json['username'],
        password = json['password'],
        ruolo = json['ruolo'],
        session = json['session'] ?? '';

  Map<String, dynamic> toJson() => {
        'nome': nome,
        'cognome': cognome,
        'username': username,
        'password': password,
        'ruolo': ruolo,
        'session': session,
      };
}
