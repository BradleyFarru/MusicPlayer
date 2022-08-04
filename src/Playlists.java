public class Playlists {
    Playlist[] playlists = new Playlist[10];

    public void createPlaylist() {
        String playlistName = "My playlist";
        Song[] playlistSongs = new Song[10];

        Playlist playlist = new Playlist();
        playlist.name = playlistName;
        playlist.songs = playlistSongs;

        int emptyPosition = Utilities.findEmptyPosition(playlists);
        if (emptyPosition == -1) {
            int newLength = playlists.length + 10; playlists = (Playlist[]) Utilities.copyArray(playlists, newLength);
        }
        playlists[emptyPosition] = playlist;
    }

    public void removeSong() {
        String title = "Sweet Child O' Mine";
        for (int i = 0; i < playlists.length; i++) {
            if (playlists[i].name.equals(title)) {
                playlists[i] = null;
                break;
            }
        }
    }
}
