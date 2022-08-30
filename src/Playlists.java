public class Playlists implements java.io.Serializable {
    static Playlist[] playlists = new Playlist[10];

    public static Playlist[] getPlaylists() {
        return playlists;
    }

    public static Playlist createPlaylist(String name, String[] songTitles) {
        Song[] playlistSongs = new Song[10];

        if (songTitles != null) {
            for (String songTitle : songTitles) {
                for (Song song : Songs.songs) {
                    if (song == null) { continue; }
                    if (songTitle.equals(song.title)) {
                        int emptyPosition = Utilities.findEmptyPosition(playlistSongs);
                        if (emptyPosition == -1) {
                            int newLength = playlistSongs.length + 10;
                            playlistSongs = (Song[]) Utilities.copyArray(playlistSongs, newLength);
                        }
                        playlistSongs[emptyPosition] = song;
                    }
                }
            }
        }

        Playlist playlist = new Playlist();
        playlist.name = name;
        playlist.songs = playlistSongs;

        int emptyPosition = Utilities.findEmptyPosition(playlists);
        if (emptyPosition == -1) {
            int newLength = playlists.length + 10; playlists = (Playlist[]) Utilities.copyArray(playlists, newLength);
        }
        playlists[emptyPosition] = playlist;

        String fileContent = name;

        for (Song song : playlist.songs) {
            if (song != null) {
                fileContent = fileContent.concat("\n" + song.title);
            }
        }

        FileManager.createFile("playlists", name, null, playlist, FileManager.ObjectType.PLAYLIST);

        return playlist;
    }

    public static void removePlaylist(String name) {
        for (int i = 0; i < playlists.length; i++) {
            if (playlists[i].name.equals(name)) {
                FileManager.removeFile("/playlists/" + name);
                playlists[i] = null;
                break;
            }
        }
    }
}
