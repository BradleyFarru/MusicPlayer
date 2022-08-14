public class Playlist {
    String name;
    Song[] songs;

    public Song addSong (Song providedSong) {
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
            return null;
        }

        songs[emptyPosition] = song;

        return song;
    }
    public void removeSong(String title) {
        for (int i = 0; i < songs.length; i++) {
            if (songs[i].title.equals(title)) {
                songs[i] = null;
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public Song[] getSongs() {
        return songs;
    }
}
