import java.io.IOException;
import java.io.Serializable;

public class Songs implements Serializable {
    static Song[] songs = new Song[10];

    public static void createSong(String songTitle, String songArtist, String songPath) throws IOException {
        int songLength = 5;

        Song song = new Song();
        song.filePath = songPath;
        song.title = songTitle.trim();
        song.artist = songArtist.trim();
        song.length = songLength;

        int emptyPosition = Utilities.findEmptyPosition(songs);

        if (emptyPosition == -1) {
            int newLength = songs.length + 10;
            songs = (Song[]) Utilities.copyArray(songs, newLength);
        }

        songs[emptyPosition] = song;

        FileManager.createFile("songs", song.title, song, null, FileManager.ObjectType.SONG);
    }

    
    public static void removeSong(String givenTitle) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i].title.equals(givenTitle)) {
                FileManager.removeFile("/songs/" + songs[i].title);
                songs[i] = null;
                break;
            }
        }
    }
}
