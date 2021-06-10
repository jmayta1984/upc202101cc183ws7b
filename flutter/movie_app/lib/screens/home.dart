import 'package:flutter/material.dart';
import 'package:movie_app/constants/api_path.dart';
import 'package:movie_app/screens/favorite_list.dart';
import 'package:movie_app/screens/movie_list.dart';

class Home extends StatefulWidget {
  @override
  _HomeState createState() => _HomeState();
}

class _HomeState extends State<Home> {
  // Menu options
  late String value;
  List<String> options = [urlPopular, urlUpcoming, urlTopRated];

  // Tab options
  late int selectedIndex;
  late var widgetOptions;

  @override
  void initState() {
    value = urlPopular;
    super.initState();
    selectedIndex = 0;
    widgetOptions = [
      MovieList(value),
      FavoriteList(),
    ];
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text('Movies'),
        actions: [
          PopupMenuButton<String>(
              onSelected: (option) {
                setState(() {
                  value = option;
                  widgetOptions[0] = MovieList(value);
                });
              },
              enabled: true,
              itemBuilder: (context) {
                return options.map((option) {
                  return PopupMenuItem(value: option, child: Text(option));
                }).toList();
              }),
        ],
      ),
      body: widgetOptions.elementAt(selectedIndex),
      bottomNavigationBar: BottomNavigationBar(
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.movie),
            label: 'Movies',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.favorite),
            label: 'Favorites',
          ),
        ],
        currentIndex: selectedIndex,
        onTap: onItemTapped,
      ),
    );
  }

  void onItemTapped(int index) {
    setState(() {
      selectedIndex = index;
    });
  }
}
