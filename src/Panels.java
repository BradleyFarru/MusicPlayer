import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class Panels {
    public JPanel mainPanel(String iconAddress, String title, int duration) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        ImageIcon imageIcon = new ImageIcon(iconAddress);
        Image image = imageIcon.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
        imageIcon.setImage(image);

        JPanel songTitlePanel = new JPanel();
        songTitlePanel.setLayout(new BorderLayout());

        JLabel songTitle = new JLabel();
        songTitle.setIcon(imageIcon);
        songTitle.setText(title);
        songTitle.setFont(new Font("Monospaced", Font.BOLD, 35));
        songTitle.setHorizontalTextPosition(JLabel.CENTER);
        songTitle.setVerticalTextPosition(JLabel.BOTTOM);
        songTitle.setHorizontalAlignment(JLabel.CENTER);
        songTitle.setVerticalAlignment(JLabel.CENTER);
        songTitle.setIconTextGap(20);

        songTitlePanel.add(songTitle, BorderLayout.CENTER);

        JPanel songButtonsPanel = new JPanel();
        songButtonsPanel.setLayout(new FlowLayout());

        JButton previousSongBtn = new JButton();
        previousSongBtn.setText("Previous");

        previousSongBtn.addActionListener(e -> {

        });


        JButton pausePlayBtn = new JButton();
        pausePlayBtn.setText("Pause");

        pausePlayBtn.addActionListener(e -> {
            if (pausePlayBtn.getText().equals("Pause")) {
                pausePlayBtn.setText("Play");
            } else {
                pausePlayBtn.setText("Pause");
            }
        });

        JButton nextSongBtn = new JButton();
        nextSongBtn.setText("Next");

        nextSongBtn.addActionListener(e -> {

        });

        songButtonsPanel.add(previousSongBtn);
        songButtonsPanel.add(pausePlayBtn);
        songButtonsPanel.add(nextSongBtn);

        JPanel songDurationPanel = new JPanel();
        songDurationPanel.setLayout(new FlowLayout());

        JSlider songPositionSlider = new JSlider();
        songPositionSlider.setOrientation(JSlider.HORIZONTAL);
        songPositionSlider.setPreferredSize(new Dimension(300, 20));
        songPositionSlider.setMinimum(0);
        songPositionSlider.setMaximum(duration);
        songPositionSlider.setMinorTickSpacing(1);
        songPositionSlider.setMajorTickSpacing(10);
        songPositionSlider.setValue(50);

        JLabel songPosition = new JLabel(Integer.toString(songPositionSlider.getValue()));
        songPosition.setFont(new Font("Monospaced", Font.PLAIN, 15));

        JLabel songDuration = new JLabel(Integer.toString(songPositionSlider.getMaximum()));
        songDuration.setFont(new Font("Monospaced", Font.PLAIN, 15));

        songPositionSlider.addChangeListener(e -> songPosition.setText(Integer.toString(songPositionSlider.getValue())));

        songDurationPanel.add(songPosition);
        songDurationPanel.add(songPositionSlider);
        songDurationPanel.add(songDuration);

        mainPanel.add(songTitlePanel);
        mainPanel.add(songButtonsPanel);
        mainPanel.add(songDurationPanel);

        return mainPanel;
    }

    public JPanel playlistsPanel() {
        JPanel playlistsParentPanel = new JPanel();
        playlistsParentPanel.setLayout(new FlowLayout());

        JPanel playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new FlowLayout());
        playlistsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        playlistsPanel.setSize(1000, 1000);

        JButton newPlaylistBtn = new JButton();
        newPlaylistBtn.setText("Add a new playlist");
        playlistsPanel.add(newPlaylistBtn);

        JTextArea playlistNameField = new JTextArea(1, 10);
        playlistNameField.setBackground(Color.white);
        playlistNameField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        playlistsPanel.add(playlistNameField);
        playlistNameField.setVisible(false);

        if (Playlists.getPlaylists() != null) {
            JPanel playlistsListPanel = new JPanel();
            playlistsListPanel.setLayout(new BoxLayout(playlistsListPanel, BoxLayout.PAGE_AXIS));
            for (Playlist playlist : Playlists.getPlaylists()) {
                if (playlist != null) {
                    JButton playlistButton = new JButton();
                    playlistButton.setText(playlist.getName());
                    playlistsPanel.add(playlistButton);
                }
            }
        }

        Playlist newPlaylist = Playlists.createPlaylist(null);

        newPlaylistBtn.addActionListener(e -> {
            if (playlistNameField.isVisible()) {
                if (!playlistNameField.getText().equals("")) {
                    newPlaylist.name = playlistNameField.getText();
                    JButton playlistButton = new JButton();
                    playlistButton.setText(playlistNameField.getText());
                    playlistsPanel.add(playlistButton);
                    playlistNameField.setVisible(false);
                }

            } else {
                playlistNameField.setText("");
                playlistNameField.setVisible(true);
            }
        });

        JPanel playlistPanel = new JPanel();

        JButton addSongToPlaylist = new JButton();
        addSongToPlaylist.setText("Add a song");

        addSongToPlaylist.addActionListener(e -> {
            JFileChooser songFile = new JFileChooser();
            songFile.showOpenDialog(null);
            songFile.addChoosableFileFilter(new FileNameExtensionFilter("mp3"));

            Song newSong = new Song();
            newSong.filePath = songFile.getSelectedFile().getPath();

            newPlaylist.addSong(newSong);
        });

        playlistsParentPanel.add(playlistsPanel);
        playlistsParentPanel.add(playlistPanel);

        return playlistsPanel;
    }
}
