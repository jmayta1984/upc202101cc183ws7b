import 'package:musicfy/models/artist.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';

class DbHelper {
  final int version = 1;
  Database? db;

  static final DbHelper _dbHelper = DbHelper._interal();
  DbHelper._interal();

  factory DbHelper() {
    return _dbHelper;
  }

  Future<Database?> openDb() async {
    if (db == null) {
      db = await openDatabase(
        join(await getDatabasesPath(), 'musicfy.db'),
        onCreate: (db, version) {
          db.execute(
            'CREATE TABLE artists(idArtist TEXT PRIMARY KEY, strArtist TEXT, intFormedYear TEXT, strArtistThumb TEXT)',
          );
        },
        version: version,
      );
    }
    return db;
  }

  Future insertArtist(Artist artist) async {
    int id = await db!.insert(
      'artists',
      artist.toMap(),
      conflictAlgorithm: ConflictAlgorithm.replace,
    );
    return id;
  }

  Future deleteArtist(Artist artist) async {
    int result = await db!.delete(
      'artists',
      where: 'idArtist= ?',
      whereArgs: [artist.id],
    );
    return result;
  }

  Future<List> searchArtists(String name) async {
    final artistsMap = await db!.query(
      'artists',
      where: 'strArtist LIKE ?',
      whereArgs: ['$name%'],
    );
    List artists = artistsMap.map((map) => Artist.fromJson(map)).toList();
    return artists;
  }


  Future<List> allArtists() async {
    final artistsMap = await db!.query(
      'artists'
    );
    List artists = artistsMap.map((map) => Artist.fromJson(map)).toList();
    return artists;
  }
}
