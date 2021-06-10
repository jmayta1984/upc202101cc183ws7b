import 'package:flutter/material.dart';
import 'package:movie_app/models/movie.dart';
import 'package:movie_app/utils/db_helper.dart';

class FavoriteList extends StatefulWidget {
  @override
  _FavoriteListState createState() => _FavoriteListState();
}

class _FavoriteListState extends State<FavoriteList> {
  //late List movies;
  late Future<List> futureMovies;
  late DbHelper dbHelper;

  @override
  void initState() {
    dbHelper = DbHelper();
    futureMovies = allMovies();
    super.initState();
    //allMovies();
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List>(
        future: futureMovies,
        builder: (context, snapshot) {
          if (snapshot.hasData) {
            return ListView.builder(
                itemCount: snapshot.data?.length,
                itemBuilder: (context, index) {
                  return FavoriteRow(snapshot.data?[index], dbHelper);
                });
          } else if (snapshot.hasError) {
            return Text('$snapshot.error');
          }

          return Center(
            child: CircularProgressIndicator(),
          );
        });
  }

  Future<List> allMovies() async {
    await dbHelper.openDb();
    return dbHelper.allMovies();
  }

  /*
  @override
  Widget build(BuildContext context) {
    return Container(
      child: ListView.builder(
          itemCount: movies.length,
          itemBuilder: (context, index) {
            return FavoriteRow(movies[index], dbHelper);
          }),
    );
  }
  
  Future allMovies() async {
    await dbHelper.openDb();
    List result = await dbHelper.allMovies();
    setState(() {
      movies = result;
    });
  }
  */
}

class FavoriteRow extends StatelessWidget {
  final Movie movie;
  final DbHelper dbHelper;

  FavoriteRow(this.movie, this.dbHelper);
  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        title: Text(movie.title),
        trailing: IconButton(
          icon: Icon(Icons.delete),
          onPressed: () {
            dbHelper.deleteMovie(movie);
          },
        ),
      ),
    );
  }
}
