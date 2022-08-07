import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Scanner;

public class MP3Player extends Thread {
    private final String mp3File;
    private Player jlPlayer;

    boolean continuePlaying = false;

    boolean opened = true;


    Scanner sc = new Scanner(System.in);
    String command;

    public void Menu() {
        while (opened) {
            System.out.println("In menu");
            command = sc.nextLine();

            switch (command) {
                case "play" -> {
                    play();
                }
                case "pause" -> {
                    pause();
                }
                case "resume" -> {
                    sresume();
                }
                case "stop" -> {
                    close();
                    opened = false;
                }
                default -> {
                    System.out.println("Command not recognised!");
                }
            }
        }
    }

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

    }

    public void run() {
        play();

            try {
                while (true) {
                    synchronized (this) {
                        while (!continuePlaying)
                            wait();


                        jlPlayer.play();
                        Menu();
                    }
                }
            }

            catch (Exception e) {
                System.out.println(e);
            }
    }

    public void pause() {
        continuePlaying = false;
    }

    public void sresume() {
        try {
            jlPlayer.notify();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void close() {
        if (jlPlayer != null) jlPlayer.close();
    }
}