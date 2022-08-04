import java.util.Scanner;

public class SongPlayer {
    public static void main(String[] args) {
        String filename = "While My Guitar Gently Weeps Cover.mp3";
        MP3Player mp3Player = new MP3Player(filename);
        mp3Player.play();

        Scanner sc = new Scanner(System.in);

        System.out.println("Write stop to stop the music: ");

        if (sc.nextLine().equalsIgnoreCase("stop")) {
            mp3Player.close();
        }
    }
}
