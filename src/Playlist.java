import java.io.Serializable;

public class Playlist implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    String name;
    Song[] songs;

    public void addSong (Song providedSong) {
        Song song = new Song();

        int emptyPosition = Utilities.findEmptyPosition(songs);
        if (emptyPosition == -1) {
            int newLength = songs.length + 10; songs = (Song[]) Utilities.copyArray(songs, newLength);
        }

        for (Song songInSongs : Songs.songs) {
            if (songInSongs != null) {
                if (songInSongs.equals(providedSong)) {
                    song = songInSongs;
                    break;
                }
            }
        }

        if (song.title == null) {
            return;
        }

        songs[emptyPosition] = song;

        FileManager.saveFile("null", this, providedSong);
    }
    public void removeSong(Song song) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i] == null) continue;
            if (songs[i].equals(song)) {
                songs[i] = null;
                break;
            }
        }
    }

    public String getName() {
        return name;
    }
}
