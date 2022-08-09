public class SongQueue {
    Song currentSong;

    public Song skipSong() {
        return new Song();
    }

    private Song[] createSongQueue() {
        return new Song[10];
    }
}
