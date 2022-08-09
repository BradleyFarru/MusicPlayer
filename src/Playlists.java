public class Playlists {
    static Playlist[] playlists = new Playlist[10];

    public static Playlist[] getPlaylists() {
        return playlists;
    }

    public static Playlist createPlaylist(String name) {
        String playlistName = name;
        Song[] playlistSongs = new Song[10];

        Playlist playlist = new Playlist();
        playlist.name = playlistName;
        playlist.songs = playlistSongs;

        int emptyPosition = Utilities.findEmptyPosition(playlists);
        if (emptyPosition == -1) {
            int newLength = playlists.length + 10; playlists = (Playlist[]) Utilities.copyArray(playlists, newLength);
        }
        playlists[emptyPosition] = playlist;

        return playlist;
    }

    public static void removePlaylist(String name) {
        for (int i = 0; i < playlists.length; i++) {
            if (playlists[i].name.equals(name)) {
                playlists[i] = null;
                break;
            }
        }
    }
}
