import 'dart:convert';
import 'dart:io';
import 'package:movie_app/constants/api_path.dart';
import 'package:http/http.dart' as http;
import 'package:movie_app/models/movie.dart';

class HttpHelper {
  Future<List?> getMovies(String urlOption) async {
    final strPopular = urlBase + urlOption + urlApi + urlKey;
    final url = Uri.parse(strPopular);

    http.Response result = await http.get(url);

    if (result.statusCode == HttpStatus.ok) {
      final jsonResponse = json.decode(result.body);
      final moviesMap = jsonResponse['results'];
      List movies = moviesMap.map((map) => Movie.fromJson(map)).toList();
      return movies;
    } 
    
  }
}
