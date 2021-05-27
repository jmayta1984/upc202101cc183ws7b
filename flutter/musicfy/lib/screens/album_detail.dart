import 'package:flutter/material.dart';
import 'package:musicfy/models/album.dart';
import 'package:musicfy/screens/track_list.dart';
import 'package:transparent_image/transparent_image.dart';

class AlbumDetail extends StatefulWidget {
  final Album album;
  AlbumDetail(this.album);
  @override
  _AlbumDetailState createState() => _AlbumDetailState(album);
}

class _AlbumDetailState extends State<AlbumDetail> {
  final Album album;
  _AlbumDetailState(this.album);
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: CustomScrollView(
        slivers: [
          SliverAppBar(
            expandedHeight: 300,
            centerTitle: true,
            title: Text(album.title),
            
            flexibleSpace: FlexibleSpaceBar(
              background: Hero(
                tag: album.title,
                child: FadeInImage.memoryNetwork(
                  placeholder: kTransparentImage,
                  image: album.thumb,
                  imageErrorBuilder: (context, url, error) => Icon(Icons.error),
                ),
              ),
            ),
          ),
          SliverList(
            delegate: SliverChildListDelegate([
              //AlbumHeader(album),
              TrackList(album),
            ]),
          ),
        ],
      ),
    );

    /*
       DraggableScrollableSheet(
        initialChildSize: 0.95,
        minChildSize: 0.95,
        builder: (context, controller) {
          return SingleChildScrollView(
            controller: controller,
            child: Column(
              children: [
                AlbumHeader(album),
                TrackList(album),
              ],
            ),
          );
        },
      ),
      */
  }
}

class AlbumHeader extends StatelessWidget {
  final Album album;
  AlbumHeader(this.album);
  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Hero(
          tag: album.title,
          child: FadeInImage.memoryNetwork(
            placeholder: kTransparentImage,
            image: album.thumb,
            imageErrorBuilder: (context, url, error) => Icon(Icons.error),
          ),
        ),
        Padding(
          padding: EdgeInsets.only(top: 8),
          child: Text(
            album.title,
            style:
                DefaultTextStyle.of(context).style.apply(fontSizeFactor: 1.5),
          ),
        ),
        Padding(
          padding: EdgeInsets.only(top: 8, bottom: 8),
          child: Text(
            '${album.artist}${album.year == '0' ? '' : ' ${album.year}'}',
          ),
        ),
      ],
    );
  }
}
