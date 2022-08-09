import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    Frame() {
        this.setTitle("Music Player");
        this.setSize(400, 400);
        this.setBackground(Color.white);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(true);

        ImageIcon image = new ImageIcon("Icon.png");
        this.setIconImage(image.getImage());
    }
}
