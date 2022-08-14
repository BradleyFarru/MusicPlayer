public class Songs {
    static Song[] songs = new Song[10];


    public static void uploadSong(String songTitle, String songArtist, String songPath) {
        int songLength = 5;

        Song song = new Song();
        song.filePath = songPath;
        song.title = songTitle;
        song.artist = songArtist;
        song.length = songLength;

        int emptyPosition = Utilities.findEmptyPosition(songs);

        if (emptyPosition == -1) {
            int newLength = songs.length + 10;
            songs = (Song[]) Utilities.copyArray(songs, newLength);
        }

        songs[emptyPosition] = song;
    }

    public static void removeSong(String givenTitle) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i].title.equals(givenTitle)) {
                songs[i] = null;
                break;
            }
        }
    }
}
