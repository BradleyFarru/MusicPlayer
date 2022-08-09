import javax.swing.*;

public class Display extends JFrame {

    public static void main(String[] args) {
        Frame frame = new Frame();
        Panels panels = new Panels();

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel parentPanel = new JPanel();
        parentPanel.add(panels.mainPanel("/Users/bradleyfarrugia/IdeaProjects/MusicPlayer/src/Icon.png", "Title", 100));
        parentPanel.add(panels.playlistsPanel());
        frame.add(parentPanel);
        frame.setVisible(true);
    }
}
