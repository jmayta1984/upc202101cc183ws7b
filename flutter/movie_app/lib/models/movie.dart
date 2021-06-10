class Movie {
  int id;
  String title;
  String overview;
  String poster;

  Movie({
    required this.id,
    required this.title,
    required this.overview,
    required this.poster,
  });

  // Parsing data from json for networking
  factory Movie.fromJson(Map<String, dynamic> map) {
    return Movie(
      id: map['id'],
      title: map['original_title'],
      overview: map['overview'],
      poster: map['poster_path'],
    );
  }

  // Serializing to map for local persistence
  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'title': title,
      'overview': overview,
      'path': poster,
    };
  }

  // Parsing data from map for local persistence
  factory Movie.fromMap(Map<String, dynamic> map) {
    return Movie(
      id: map['id'],
      title: map['title'],
      overview: map['overview'],
      poster: map['path'],
    );
  }
}
