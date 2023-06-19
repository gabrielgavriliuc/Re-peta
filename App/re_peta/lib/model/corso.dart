class Corso {
  String titolo;
  int CFU;
  bool stato;

  Corso(this.titolo, this.CFU, this.stato);

  Corso.fromJson(Map<String, dynamic> json)
      : titolo = json['titolo'],
        CFU = json['CFU'],
        stato = json['stato'];

  Map<String, dynamic> toJson() =>
      {'titolo': titolo, 'CFU': CFU, 'stato': stato};

  
}