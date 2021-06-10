import 'package:movie_app/models/movie.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

class DbHelper {
  final int version = 1;
  final String name = 'xtremeapp.db';
  Database? db;

  static final DbHelper _dbHelper = DbHelper._interal();

  DbHelper._interal();

  factory DbHelper() {
    return _dbHelper;
  }

  Future<Database?> openDb() async {
    if (db == null) {
      db = await openDatabase(
        join(await getDatabasesPath(), name),
        onCreate: (database, version) {
          database.execute(
            'CREATE TABLE movies(id INTEGER PRIMARY KEY, title TEXT, overview TEXT, path TEXT)',
          );
        },
        version: version,
      );
    }
    return db;
  }

  Future insertMovie(Movie movie) async {
    int result = await db!.insert(
      'movies',
      movie.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return result;
  }

  Future deleteMovie(Movie movie) async {
    int result = await db!.delete(
      'movies',
      where: 'id= ?',
      whereArgs: [movie.id],
    );
    return result;
  }

  Future<List> allMovies() async {
    final moviesMap = await db!.query('movies');
    List movies = moviesMap.map((map) => Movie.fromMap(map)).toList();
    print('Movies: ${movies.length}');
    return movies;
  }

  Future<bool> isFavorite(Movie movie) async {
    final moviesMap =
        await db!.query('movies', where: 'id = ?', whereArgs: [movie.id]);
    return moviesMap.length > 0;
  }
}
