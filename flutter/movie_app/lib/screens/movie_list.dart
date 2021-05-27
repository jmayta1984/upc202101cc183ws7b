import 'package:flutter/material.dart';
import 'package:movie_app/constants/api_path.dart';
import 'package:movie_app/utils/http_helper.dart';
import 'package:transparent_image/transparent_image.dart';

class MovieList extends StatefulWidget {
  @override
  _MovieListState createState() => _MovieListState();
}

class _MovieListState extends State<MovieList> {
  late List movies;
  late HttpHelper httpHelper;

  @override
  void initState() {
    movies = [];
    httpHelper = HttpHelper();
    initialize();
    super.initState();
  }

  Future initialize() async {
    httpHelper.popularMovies().then((value) {
      setState(() {
        movies = value ?? [];
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text('Movies'),
        
      ),
      body: ListView.builder(
          itemCount: movies.length,
          itemBuilder: (BuildContext context, int index) {
            return Card(
              child: ListTile(
                leading: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Expanded(
                      child: FadeInImage.memoryNetwork(
                        placeholder: kTransparentImage,
                        image: urlImage + movies[index].poster,
                        imageErrorBuilder: (context, url, error) =>
                            Icon(Icons.error),
                      ),
                    ),
                  ],
                ),
                title: Text(
                  movies[index].title,
                  maxLines: 1,
                ),
                subtitle: Text(
                  movies[index].overview,
                  maxLines: 1,
                ),
              ),
            );
          }),
    );
  }
}
