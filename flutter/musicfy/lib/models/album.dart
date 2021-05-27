class Album {
  String id;
  String title;
  String thumb;
  String artist;
  String year;
  String description;

  Album({
    required this.id,
    required this.title,
    required this.thumb,
    required this.artist,
    required this.year,
    required this.description,
  });

  factory Album.fromMap(Map<String, dynamic> map) {
    return Album(
      id: map['idAlbum'],
      title: map['strAlbum'],
      thumb: map['strAlbumThumb']??'',
      artist: map['strArtist'],
      year: map['intYearReleased'],
      description: map['strDescriptionEN'] ?? '',
    );
  }

  factory Album.fromJson(Map<String, dynamic> map) => Album.fromMap(map);
}
