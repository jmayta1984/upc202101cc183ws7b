class Track {
  String title;
  String artist;
  String duration;

  Track({
    required this.title,
    required this.artist,
    required this.duration,
  });

  factory Track.fromMap(Map<String, dynamic> map) {
    return Track(
      title: map['strTrack'],
      artist: map['strArtist'],
      duration: map['intDuration'],
    );
  }

  factory Track.fromJson(Map<String, dynamic> map) => Track.fromMap(map);
}
