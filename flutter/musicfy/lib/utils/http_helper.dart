import 'dart:convert';
import 'dart:io';
import 'package:http/http.dart' as http;
import 'package:musicfy/constants/api_path.dart';
import 'package:musicfy/models/album.dart';
import 'package:musicfy/models/artist.dart';
import 'package:musicfy/models/track.dart';

class HttpHelper {
  /*
  A Future is used to represent a potential value, or error, that will be available at some time
  in the future. Basically, when a function returns a Future, it means that it takes a while for
  its result to be ready, and the result will be available in the future. The Future itself is
  returned immediately and its underlying object is returned at some time in the future.
  Writing Future<String> means that the function will immediately return a Future
  without interrupting the code, and then, when it completes retrieving all of the data, it will
  return String.
  */
  Future<List> searchAlbums(String artist) async {
    final searchAlbums = urlBase + urlKey + urlAlbum + artist;
    var url = Uri.parse(searchAlbums);
    http.Response result = await http.get(url);
    if (result.statusCode == HttpStatus.ok) {
      final jsonResponse = json.decode(result.body);
      final albumsMap = jsonResponse['album'];
      List albums = albumsMap.map((map) => Album.fromJson(map)).toList();
      return albums;
    } else {
      return [];
    }
  }

  Future<List> searchTracks(String album) async {
    final searchTracks = urlBase + urlKey + urlTrack + album;
    var url = Uri.parse(searchTracks);
    http.Response result = await http.get(url);
    if (result.statusCode == HttpStatus.ok) {
      final jsonResponse = json.decode(result.body);
      final tracksMap = jsonResponse['track'];
      List tracks = tracksMap.map((map) => Track.fromJson(map)).toList();
      return tracks;
    } else {
      return [];
    }
  }

  Future<List> searchArtists(String name) async {
    final searchArtists = urlBase + urlKey + urlArtist + name;
    print(searchArtists);
    var url = Uri.parse(searchArtists);
    http.Response result = await http.get(url);
    if (result.statusCode == HttpStatus.ok) {
      final jsonResponse = json.decode(result.body);
      final artistsMap = jsonResponse['artists'] ?? [];
      List artists =
          artistsMap.map((map) => Artist.fromJson(map)).toList();
      return artists;
    } else {
      return [];
    }
  }
}
