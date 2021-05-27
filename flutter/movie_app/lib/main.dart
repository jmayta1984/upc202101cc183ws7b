import 'package:flutter/material.dart';
import 'package:movie_app/screens/movie_list.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Movies',
      theme: ThemeData.light(),
      darkTheme: ThemeData.dark(),
      home: MovieList(),
    );
  }
}
