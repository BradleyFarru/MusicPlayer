import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

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
        JPanel playlistsPanel = new JPanel();
        playlistsPanel.setLayout(new FlowLayout());
        playlistsPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        playlistsPanel.setSize(1000, 1000);

        JButton newPlaylistBtn = new JButton();
        newPlaylistBtn.setText("Add a new playlist");
        playlistsPanel.add(newPlaylistBtn);

        JTextArea playlistNameField = new JTextArea(1, 15);
        playlistNameField.setBackground(Color.white);
        playlistNameField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        playlistsPanel.add(playlistNameField);
        playlistNameField.setVisible(false);

        playlistNameField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String name = playlistNameField.getText();
                int length = name.length();

                if(length >= 50) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        if (Playlists.getPlaylists() != null) {
            JPanel playlistsListPanel = new JPanel();
            playlistsListPanel.setLayout(new BoxLayout(playlistsListPanel, BoxLayout.PAGE_AXIS));
            for (Playlist playlist : Playlists.getPlaylists()) {
                if (playlist != null) {
                    JButton playlistButton = new JButton();
                    playlistButton.setText(playlist.getName());
                    playlistsPanel.add(playlistButton);

                    AtomicBoolean isShowingPanel = new AtomicBoolean(false);

                    playlistButton.addActionListener(e -> {
                        if (!isShowingPanel.get()) {
                            JComponent currentPlaylistPanel = (JComponent) playlistsPanel.getClientProperty("playlist panel");
                            if (currentPlaylistPanel != null) {
                                playlistsPanel.remove(currentPlaylistPanel);
                            }

                            JPanel thisPlaylistPanel = playlistPanel(playlist);
                            thisPlaylistPanel.setLayout(new FlowLayout());
                            thisPlaylistPanel.setBorder(BorderFactory.createLineBorder(Color.black, 50));
                            playlistsPanel.putClientProperty("playlist panel", thisPlaylistPanel);
                            playlistsPanel.add(thisPlaylistPanel);
                        } else {
                            JComponent currentPlaylistPanel = (JComponent) playlistsPanel.getClientProperty("playlist panel");
                            playlistsPanel.remove(currentPlaylistPanel);
                        }
                    });
                }
            }
        }


        newPlaylistBtn.addActionListener(e -> {
            if (playlistNameField.isVisible()) {
                if (!playlistNameField.getText().equals("")) {
                    Playlist newPlaylist = Playlists.createPlaylist(null);
                    newPlaylist.name = playlistNameField.getText();

                    JButton playlistButton = new JButton();
                    playlistButton.setText(newPlaylist.name);
                    playlistsPanel.add(playlistButton);

                    AtomicBoolean isShowingPanel = new AtomicBoolean(false);

                    playlistButton.addActionListener(e1 -> {
                        if (!isShowingPanel.get()) {
                            JComponent currentPlaylistPanel = (JComponent) playlistsPanel.getClientProperty("playlist panel");
                            if (currentPlaylistPanel != null) {
                                playlistsPanel.remove(currentPlaylistPanel);
                            }

                            playlistsPanel.add(playlistsPanel.add(playlistPanel(newPlaylist)));
                            playlistsPanel.putClientProperty("playlist panel", playlistsPanel);
                            isShowingPanel.set(true);
                        } else {
                            JComponent currentPlaylistPanel = (JComponent) playlistsPanel.getClientProperty("playlist panel");
                            playlistsPanel.remove(currentPlaylistPanel);
                        }
                    });

                    playlistNameField.setVisible(false);
                }

            } else {
                playlistNameField.setText("");
                playlistNameField.setVisible(true);
            }
        });



        return playlistsPanel;
    }

    public JPanel playlistPanel(Playlist playlist) {
        JPanel playlistPanel = new JPanel();
        playlistPanel.setLayout(new FlowLayout());

        JButton addSongToPlaylist = new JButton();
        addSongToPlaylist.setText("Add a song");

        for (Song song : playlist.songs) {
            if (song != null) {
                JButton songButton = createSongButton(song);
                playlistPanel.add(songButton);
            }
        }

        return playlistPanel;
    }

    public JButton createSongButton(Song song) {
        JButton songButton = new JButton();
        songButton.setText(song.title);

        songButton.addActionListener(e -> songButton.setText("Playing!"));

        return songButton;
    }

    public JPanel uploadSongPanel() {
        JPanel uploadSongPanel = new JPanel();

       JButton uploadSongButton = new JButton();
       uploadSongButton.setText("Upload Song");
       uploadSongPanel.add(uploadSongButton);

       AtomicReference<String> songFilePath = new AtomicReference<>("");
       AtomicBoolean songFilePathProvided = new AtomicBoolean(false);

        uploadSongButton.addActionListener(e -> {
            JFileChooser songFile = new JFileChooser();
            uploadSongPanel.add(songFile);
            int result = songFile.showOpenDialog(null);
            songFile.addChoosableFileFilter(new FileNameExtensionFilter("MP3 FILE","mp3"));

            if (result == JFileChooser.APPROVE_OPTION) {
                songFilePath.set(songFile.getSelectedFile().getPath());
                songFilePathProvided.set(true);
                songFile.setDialogTitle("Open mp3");
            }

            JLabel songTitleLabel = new JLabel();
            songTitleLabel.setText("Name");
            JTextArea songTitleField = new JTextArea(1, 15);
            songTitleField.setBackground(Color.white);
            songTitleField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
            uploadSongPanel.add(songTitleLabel);
            uploadSongPanel.add(songTitleField);

            JLabel songArtistLabel = new JLabel();
            songArtistLabel.setText("Artist");
            JTextArea songArtistField = new JTextArea(1, 15);
            songArtistField.setBackground(Color.white);
            songArtistField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
            uploadSongPanel.add(songArtistLabel);
            uploadSongPanel.add(songArtistField);

            JButton submitButton = new JButton();
            submitButton.setText("Submit");
            uploadSongPanel.add(submitButton);

            JLabel errorField = new JLabel();
            errorField.setText("Please fill out all fields!");
            errorField.setVisible(false);
            uploadSongPanel.add(errorField);

            songTitleField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    errorField.setVisible(false);
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            songArtistField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                    errorField.setVisible(false);
                }

                @Override
                public void keyPressed(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

            submitButton.addActionListener(e2 -> {
                String songTitleValue = songTitleField.getText();
                String songArtistValue = songArtistField.getText();
               if (!songTitleValue.equals("") && !songArtistValue.equals("") && songFilePathProvided.get()) {
                   Songs.uploadSong(songTitleValue, songArtistValue, songFilePath.get());
                   songArtistLabel.setVisible(false);
                   songArtistField.setText("");
                   songArtistField.setVisible(false);
                   songTitleField.setVisible(false);
                   songTitleField.setText("");
                   songTitleLabel.setVisible(false);
                   submitButton.setVisible(false);
               } else {
                    errorField.setVisible(true);
               }
            });
        });

        return uploadSongPanel;
    }

}
