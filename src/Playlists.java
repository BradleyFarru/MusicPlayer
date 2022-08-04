public class Playlists {
    Playlist[] playlists = new Playlist[10];

    public void CreatePlaylist() {
        String playlistName = "My playlist";
        Song[] playlistSongs = new Song[10];

        Playlist playlist = new Playlist();
        playlist.name = playlistName;
        playlist.songs = playlistSongs;

        int emptyPosition = Utilities.FindEmptyPosition(playlists);
        if (emptyPosition == -1) {
            int newLength = playlists.length + 10; playlists = (Playlist[]) Utilities.CopyArray(playlists, newLength);
        }
        playlists[emptyPosition] = playlist;
    }

    public void RemoveSong() {
        String title = "Sweet Child O' Mine";
        for (int i = 0; i < playlists.length; i++) {
            if (playlists[i].name.equals(title)) {
                playlists[i] = null;
                break;
            }
        }
    }
}
