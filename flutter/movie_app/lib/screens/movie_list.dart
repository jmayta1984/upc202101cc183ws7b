import 'package:flutter/material.dart';
import 'package:movie_app/constants/api_path.dart';
import 'package:movie_app/models/movie.dart';
import 'package:movie_app/utils/db_helper.dart';
import 'package:movie_app/utils/http_helper.dart';
import 'package:transparent_image/transparent_image.dart';

class MovieList extends StatefulWidget {
  final String urlOption;

  MovieList(this.urlOption);

  @override
  _MovieListState createState() => _MovieListState();
}

class _MovieListState extends State<MovieList> {
  //late List movies;
  late Future<List?> futureMovies;
  late HttpHelper httpHelper;

  @override
  void initState() {
    //movies = [];
    httpHelper = HttpHelper();
    futureMovies = initialize(widget.urlOption);
    //initialize(widget.urlOption);
    super.initState();
  }

  Future<List?> initialize(String urlOption)  {
    return httpHelper.getMovies(urlOption);
  }

  /*
  Future initialize(String urlOption) async {
    httpHelper.getMovies(urlOption).then((value) {
      setState(() {
        movies = value ?? [];
      });
    });
  }
  */

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List?>(
      future: futureMovies,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return ListView.builder(
              itemCount: snapshot.data?.length,
              itemBuilder: (BuildContext context, int index) {
                return MovieRow(snapshot.data?[index]);
              });
        } else if (snapshot.hasError) {
          Text('${snapshot.error}');
        }
        return Center(
          child: CircularProgressIndicator(),
        );
      },
    );
  }
  /*
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        itemCount: movies.length,
        itemBuilder: (BuildContext context, int index) {
          return MovieRow(movies[index]);
        });
  }
  */
}

class MovieRow extends StatefulWidget {
  final Movie movie;
  MovieRow(this.movie);

  @override
  _MovieRowState createState() => _MovieRowState();
}

class _MovieRowState extends State<MovieRow> {
  late bool favorite;
  late DbHelper dbHelper;

  Future isFavorite() async {
    await dbHelper.openDb();
    final result = await dbHelper.isFavorite(widget.movie);
    setState(() {
      favorite = result;
    });
  }

  @override
  void initState() {
    favorite = false;
    dbHelper = DbHelper();
    isFavorite();
    super.initState();
  }

  @override
  void setState(fn) {
    if (mounted) {
      super.setState(fn);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        leading: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Expanded(
              child: FadeInImage.memoryNetwork(
                placeholder: kTransparentImage,
                image: urlImage + widget.movie.poster,
                imageErrorBuilder: (context, url, error) => Icon(Icons.error),
              ),
            ),
          ],
        ),
        title: Text(
          widget.movie.title,
          maxLines: 1,
        ),
        subtitle: Text(
          widget.movie.overview,
          maxLines: 1,
        ),
        trailing: IconButton(
          onPressed: () {
            favorite
                ? dbHelper.deleteMovie(widget.movie)
                : dbHelper.insertMovie(widget.movie);
            setState(() {
              favorite = !favorite;
            });
          },
          color: favorite ? Colors.red : Colors.grey,
          icon: Icon(Icons.favorite),
        ),
      ),
    );
  }
}
