import 'package:flutter/material.dart';
import 'package:musicfy/models/album.dart';
import 'package:musicfy/models/track.dart';
import 'package:musicfy/utils/http_helper.dart';

class TrackList extends StatefulWidget {
  final Album album;
  TrackList(this.album);
  @override
  _TrackListState createState() => _TrackListState(album);
}

class _TrackListState extends State<TrackList> {
  late List tracks;
  late HttpHelper httpHelper;
  final Album album;
  _TrackListState(this.album);

  @override
  void initState() {
    tracks = [];
    httpHelper = HttpHelper();
    initialize();
    super.initState();
  }

  Future initialize() async {
    httpHelper.searchTracks(album.id).then((value) {
      setState(() {
        tracks = value;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
        shrinkWrap: true,
        physics: NeverScrollableScrollPhysics(),
        itemCount: tracks.length,
        itemBuilder: (context, index) {
          return TrackRow(tracks[index]);
        });
  }
}

class TrackRow extends StatelessWidget {
  final Track track;
  TrackRow(this.track);

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        title: Text(track.title),
        subtitle: Text(track.artist),
      ),
    );
  }
}
