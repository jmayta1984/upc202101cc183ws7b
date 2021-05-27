import 'package:flutter/material.dart';
import 'package:musicfy/constants/api_path.dart';
import 'package:musicfy/models/album.dart';
import 'package:musicfy/screens/album_detail.dart';
import 'package:musicfy/utils/db_helper.dart';
import 'package:musicfy/utils/http_helper.dart';
import 'package:transparent_image/transparent_image.dart';

class AlbumList extends StatefulWidget {
  @override
  _AlbumListState createState() => _AlbumListState();
}

class _AlbumListState extends State<AlbumList> {
  late List albums;
  late HttpHelper httpHelper;
  late bool listMode;

  @override
  void initState() {
    albums = [];
    httpHelper = HttpHelper();
    listMode = false;
    super.initState();
  }

  Future searchAlbums(String artist) async {
    httpHelper.searchAlbums(artist).then((value) {
      setState(() {
        albums = value;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        centerTitle: true,
        title: Text(appTitle),
        actions: [
          IconButton(
            icon: Icon(Icons.search),
            onPressed: () async {
              final artist = await showSearch(
                context: context,
                delegate: AlbumSearch(),
              );

              if (artist != null) {
                searchAlbums(artist);
              } else {
                setState(() {
                  albums = [];
                });
              }
            },
          ),
          IconButton(
            icon: Icon(Icons.list),
            onPressed: onChange,
          )
        ],
      ),
      body: Container(
        child: listMode
            ? ListView.builder(
                itemExtent: 80,
                itemCount: albums.length,
                itemBuilder: (context, index) {
                  return AlbumRow(albums[index]);
                },
              )
            : GridView.builder(
                itemCount: albums.length,
                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                  crossAxisCount: 2,
                ),
                itemBuilder: (BuildContext context, int index) {
                  return AlbumRow(albums[index]);
                },
              ),
      ),
    );
  }

  void onChange() {
    setState(() {
      listMode = !listMode;
    });
  }
}

class AlbumRow extends StatelessWidget {
  final Album album;
  AlbumRow(this.album);

  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (_) => AlbumDetail(album),
          ),
        );
      },
      child: Card(
        child: Padding(
          padding: EdgeInsets.only(
            top: 8,
            bottom: 8,
          ),
          child: Column(
            children: [
              Expanded(
                child: Hero(
                  tag: album.title,
                  child: FadeInImage.memoryNetwork(
                    placeholder: kTransparentImage,
                    image: album.thumb,
                    imageErrorBuilder: (context, url, error) =>
                        Icon(Icons.error),
                  ),
                ),
              ),
              Text(
                album.title,
                maxLines: 1,
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class AlbumSearch extends SearchDelegate<String?> {
  final httpHelper = HttpHelper();
  final dbHelper = DbHelper();

  List albums = [];

  @override
  List<Widget> buildActions(BuildContext context) {
    return [
      IconButton(
        onPressed: () {
          if (query.isEmpty) {
            close(context, null);
          } else {
            query = '';
            showSuggestions(context);
          }
        },
        icon: Icon(Icons.clear),
      )
    ];
  }

  @override
  Widget buildLeading(BuildContext context) {
    return IconButton(
      onPressed: () {
        close(context, null);
      },
      icon: Icon(Icons.arrow_back),
    );
  }

  @override
  Widget buildResults(BuildContext context) {
    return FutureBuilder<List>(
      future: httpHelper.searchArtists(query),
      builder: (context, snapshot) {
        switch (snapshot.connectionState) {
          case ConnectionState.waiting:
            return Center(child: CircularProgressIndicator());
          default:
            if (snapshot.data!.isEmpty) {
              return Center(
                child: Text('No results!'),
              );
            } else {
              return buildResultSuccess(snapshot.data!);
            }
        }
      },
    );
  }

  @override
  Widget buildSuggestions(BuildContext context) {
    dbHelper.openDb();

    return FutureBuilder<List>(
      future:
          query.isEmpty ? dbHelper.allArtists() : dbHelper.searchArtists(query),
      builder: (context, snapshot) {
        switch (snapshot.connectionState) {
          case ConnectionState.waiting:
            return Center(child: CircularProgressIndicator());
          default:
            if (snapshot.data!.isEmpty) {
              return buildNoSuggestions();
            } else {
              return buildSuggestionsSucess(snapshot.data!);
            }
        }
      },
    );
  }

  Widget buildNoSuggestions() {
    return Center(
      child: Text(
        'No suggestions!',
      ),
    );
  }

  Widget buildSuggestionsSucess(List suggestions) {
    return ListView.builder(
        itemCount: suggestions.length,
        itemBuilder: (context, index) {
          final suggestion = suggestions[index].name;
          final suggestionId = suggestions[index].id;
          final queryText = suggestion.substring(0, query.length);
          final remainingText = suggestion.substring(query.length);

          return Card(
            child: ListTile(
              onTap: () {
                query = '';
                close(context, suggestionId);
              },
              trailing: IconButton(
                icon: Icon(Icons.delete),
                onPressed: () {
                  dbHelper.deleteArtist(suggestions[index]);
                },
              ),
              leading: FadeInImage.memoryNetwork(
                placeholder: kTransparentImage,
                image: suggestions[index].thumb,
                imageErrorBuilder: (context, url, error) => Icon(Icons.error),
              ),
              title: RichText(
                text: TextSpan(
                    text: queryText,
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                    ),
                    children: [
                      TextSpan(
                        text: remainingText,
                        style: TextStyle(
                          fontWeight: FontWeight.normal,
                        ),
                      ),
                    ]),
              ),
              subtitle: Text(suggestions[index].year),
            ),
          );
        });
  }

  Widget buildResultSuccess(List artists) {
    return ListView.builder(
        itemCount: artists.length,
        itemBuilder: (context, index) {
          return GestureDetector(
            onTap: () {
              dbHelper.insertArtist(artists[index]);
              close(context, artists[index].id);
            },
            child: Card(
              child: ListTile(
                title: Text(artists[index].name),
                subtitle: Text(artists[index].year),
                leading: FadeInImage.memoryNetwork(
                  placeholder: kTransparentImage,
                  image: artists[index].thumb,
                  imageErrorBuilder: (context, url, error) => Icon(Icons.error),
                ),
              ),
            ),
          );
        });
  }
}
