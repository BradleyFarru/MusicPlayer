import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class MP3Player {
    private final String mp3File;
    private Player jlPlayer;

    public MP3Player(String mp3File) {
        this.mp3File = mp3File;
    }

    public void play() {
        try {
            FileInputStream fileInputStream = new FileInputStream(mp3File);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            jlPlayer = new Player(bufferedInputStream);
        } catch (Exception e) {
            System.out.println("Problem playing mp3 file " + mp3File);
            System.out.println(e.getMessage());
        }

        new Thread(() -> {
            try {
                jlPlayer.play();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).start();


    }

    public void close() {
        if (jlPlayer != null) jlPlayer.close();
    }
}
