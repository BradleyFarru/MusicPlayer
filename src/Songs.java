public class Songs {
    Song[] songs = new Song[10];

    public void uploadSong() {
        String songTitle = "Sweet Child O' Mine";
        String songArtist = "Guns N' Roses";
        String songAlbum = "Appetite for Destruction";
        int songLength = 5;

        Song song = new Song();
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

    public void removeSong(String givenTitle) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i].title.equals(givenTitle)) {
                songs[i] = null;
                break;
            }
        }
    }
}
