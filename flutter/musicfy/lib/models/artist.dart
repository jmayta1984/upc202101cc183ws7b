class Artist {
  String id;
  String name;
  String year;
  String thumb;

  Artist({
    required this.id,
    required this.name,
    required this.year,
    required this.thumb,
  });

  factory Artist.fromMap(Map<String, dynamic> map) {
    return Artist(
      id: map['idArtist'],
      name: map['strArtist'],
      year: map['intFormedYear'],
      thumb: map['strArtistThumb'] ?? "",
    );
  }

  factory Artist.fromJson(Map<String, dynamic> map) => Artist.fromMap(map);

  Map<String, dynamic> toMap() {
    return {
      'idArtist': id,
      'strArtist': name,
      'intFormedYear': year,
      'strArtistThumb': thumb,
    };
  }
}
