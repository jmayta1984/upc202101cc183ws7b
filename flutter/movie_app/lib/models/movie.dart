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

  factory Movie.fromJson(Map<String, dynamic> map) {
    return Movie(
      id: map['id'],
      title: map['original_title'],
      overview: map['overview'],
      poster: map['poster_path'],
    );
  }
}
