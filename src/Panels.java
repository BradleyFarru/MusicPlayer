import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class Panels {
    enum ShowSongsType {
        PLAYSONG,
        ADDSONGTOPLAYLIST,
        REMOVESONG
    }

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
        playlistsPanel.setSize(1000, 1000);

        JButton newPlaylistBtn = new JButton();
        newPlaylistBtn.setText("Add a new playlist");
        playlistsPanel.add(newPlaylistBtn);

        JTextArea playlistNameField = new JTextArea(1, 15);
        playlistNameField.setBackground(Color.white);
        playlistNameField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
        playlistsPanel.add(playlistNameField);
        playlistNameField.setVisible(false);

        JButton submitPlaylistBtn = new JButton();
        submitPlaylistBtn.setText("Submit");
        playlistsPanel.add(submitPlaylistBtn);
        submitPlaylistBtn.setVisible(false);

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
                            playlistsPanel.putClientProperty("playlist panel", thisPlaylistPanel);
                            playlistsPanel.add(thisPlaylistPanel);
                            thisPlaylistPanel.setVisible(true);
                        } else {
                            JComponent currentPlaylistPanel = (JComponent) playlistsPanel.getClientProperty("playlist panel");
                            playlistsPanel.remove(currentPlaylistPanel);
                        }
                    });
                }
            }
        }

        newPlaylistBtn.addActionListener(e -> {
            playlistNameField.setVisible(!playlistNameField.isVisible());
            submitPlaylistBtn.setVisible(!submitPlaylistBtn.isVisible());
        });

        submitPlaylistBtn.addActionListener(e -> {
            if (!playlistNameField.getText().equals("")) {
                Playlist newPlaylist = Playlists.createPlaylist(playlistNameField.getText(), null);

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

                        JPanel thisPlaylistPanel = playlistPanel(newPlaylist);
                        playlistsPanel.add(thisPlaylistPanel);
                        thisPlaylistPanel.setVisible(true);
                        playlistsPanel.putClientProperty("playlist panel", playlistsPanel);
                        isShowingPanel.set(true);
                    } else {
                        JComponent currentPlaylistPanel = (JComponent) playlistsPanel.getClientProperty("playlist panel");
                        playlistsPanel.remove(currentPlaylistPanel);
                        isShowingPanel.set(false);
                    }
                });

                playlistNameField.setVisible(false);
                submitPlaylistBtn.setVisible(false);
            }
        });



        return playlistsPanel;
    }

    public JPanel playlistPanel(Playlist playlist) {
        JPanel playlistPanel = new JPanel();
        playlistPanel.setLayout(new FlowLayout());

        JButton addSongToPlaylistBtn = new JButton();
        addSongToPlaylistBtn.setText("Add a song");
        playlistPanel.add(addSongToPlaylistBtn);

        final Song[][] songsShowing = {new Song[Songs.songs.length]};

        int emptyPosition = Utilities.findEmptyPosition(songsShowing[0]);
        if (emptyPosition == -1) {
            int newLength = songsShowing[0].length + 10;
            songsShowing[0] = (Song[]) Utilities.copyArray(songsShowing[0], newLength);
        }

        AtomicBoolean showingSongs = new AtomicBoolean(false);
        JPanel songsShowingPanel = showSongs(songsShowing, emptyPosition, ShowSongsType.ADDSONGTOPLAYLIST, playlist);
        addSongToPlaylistBtn.add(songsShowingPanel);
        songsShowingPanel.setVisible(false);

        addSongToPlaylistBtn.addActionListener(e -> {
            songsShowingPanel.setVisible(!showingSongs.get());
            songsShowingPanel.removeAll();
            songsShowingPanel.add(showSongs(songsShowing, emptyPosition, ShowSongsType.ADDSONGTOPLAYLIST, playlist));
            showingSongs.set(!showingSongs.get());
        });

        return playlistPanel;
    }

    public JPanel songsPanel() {
        JPanel songsPanel = new JPanel();
        songsPanel.setLayout(new FlowLayout());
        songsPanel.add(uploadSongPanel());
        songsPanel.add(removeSongPanel());
        songsPanel.add(showSongsPanel());
        return songsPanel;
    }

    public JPanel uploadSongPanel() {
        JPanel uploadSongPanel = new JPanel();
        uploadSongPanel.setLayout(new BoxLayout(uploadSongPanel, BoxLayout.Y_AXIS));

        JLabel wrongExtensionField = new JLabel();
        wrongExtensionField.setText("MP3 type file only supported!");
        wrongExtensionField.setVisible(false);
        uploadSongPanel.add(wrongExtensionField);

       JButton uploadSongButton = new JButton();
       uploadSongButton.setText("Upload Song");
       uploadSongPanel.add(uploadSongButton);

       AtomicBoolean songFilePathProvided = new AtomicBoolean(false);

       AtomicBoolean submitted = new AtomicBoolean(false);

        JLabel songTitleLabel = new JLabel();
        JTextArea songTitleField = new JTextArea(1, 15);
        JLabel songArtistLabel = new JLabel();
        JTextArea songArtistField = new JTextArea(1, 15);
        JButton changePathButton = new JButton();
        JLabel currentPathLabel = new JLabel();
        JButton submitButton = new JButton();
        JLabel errorField = new JLabel();
        JFileChooser songFileChooser = new JFileChooser();

        uploadSongButton.addActionListener(e -> {

            uploadSongPanel.add(songFileChooser);
            int result = songFileChooser.showOpenDialog(null);
            songFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MP3 FILE", "mp3"));

            if (result == JFileChooser.APPROVE_OPTION) {
                String songPath = songFileChooser.getSelectedFile().getPath();
                if (songPath.endsWith(".mp3")) {
                    wrongExtensionField.setVisible(false);
                    songFilePathProvided.set(true);
                    songTitleField.setText("");
                    songArtistField.setText("");
                    uploadSongButton.setVisible(false);
                } else {
                    wrongExtensionField.setVisible(true);
                }
            }

            changePathButton.setText("Change song path");
            uploadSongPanel.add(changePathButton);

            changePathButton.addActionListener(e1 -> {
                int newResult = songFileChooser.showOpenDialog(null);
                songFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("MP3 FILE", "mp3"));

                if (newResult == JFileChooser.APPROVE_OPTION) {
                    if (songFileChooser.getSelectedFile().getPath().endsWith(".mp3")) {

                        wrongExtensionField.setVisible(false);
                        songFilePathProvided.set(true);
                    } else {
                        wrongExtensionField.setVisible(true);
                    }
                }
            });

            if (songFilePathProvided.get()) {
                uploadSongPanel.add(currentPathLabel);
                currentPathLabel.setText(songFileChooser.getSelectedFile().getName());

                songTitleLabel.setText("Name");
                songTitleField.setBackground(Color.white);
                songTitleField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
                int index = songFileChooser.getSelectedFile().getName().lastIndexOf(".");
                String filename = songFileChooser.getSelectedFile().getName().substring(0, index);
                songTitleField.setText(filename);
                uploadSongPanel.add(songTitleLabel);
                uploadSongPanel.add(songTitleField);

                songArtistLabel.setText("Artist");
                songArtistField.setBackground(Color.white);
                songArtistField.getDocument().putProperty("filterNewlines", Boolean.TRUE);
                uploadSongPanel.add(songArtistLabel);
                uploadSongPanel.add(songArtistField);

                submitButton.setText("Submit");
                uploadSongPanel.add(submitButton);

                errorField.setText("Please fill out all fields!");
                errorField.setVisible(false);
                uploadSongPanel.add(errorField);

                songTitleField.setVisible(true);
                songTitleLabel.setVisible(true);
                songArtistLabel.setVisible(true);
                songArtistField.setVisible(true);
                submitButton.setVisible(true);
                changePathButton.setVisible(true);

                songTitleField.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        errorField.setVisible(false);
                        wrongExtensionField.setVisible(false);
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
                        wrongExtensionField.setVisible(false);
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
                        try {
                            Songs.createSong(songTitleValue, songArtistValue, songFileChooser.getSelectedFile().getPath());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        songArtistLabel.setVisible(false);
                        songArtistField.setText("");
                        songArtistField.setVisible(false);
                        songTitleField.setVisible(false);
                        songTitleField.setText("");
                        songTitleLabel.setVisible(false);
                        submitButton.setVisible(false);
                        changePathButton.setVisible(false);
                        errorField.setVisible(false);
                        currentPathLabel.setVisible(false);
                        uploadSongButton.setVisible(true);
                        submitted.set(true);
                    } else {
                        if (!submitted.get()) {
                            errorField.setVisible(true);
                        }
                    }
                });
            }
        });

        return uploadSongPanel;
    }

    public JPanel removeSongPanel() {
       JPanel removeSongPanel = new JPanel();
       removeSongPanel.setLayout(new BoxLayout(removeSongPanel, BoxLayout.Y_AXIS));

       JButton removeSongButton = new JButton();
       removeSongPanel.add(removeSongButton);
       removeSongButton.setText("Remove a Song");

        final Song[][] songsShowing = {new Song[Songs.songs.length]};

        int emptyPosition = Utilities.findEmptyPosition(songsShowing[0]);
        if (emptyPosition == -1) {
            int newLength = songsShowing[0].length + 10;
            songsShowing[0] = (Song[]) Utilities.copyArray(songsShowing[0], newLength);
        }

        AtomicBoolean showingSongs = new AtomicBoolean(false);
        JPanel songsShowingPanel = showSongs(songsShowing, emptyPosition, ShowSongsType.REMOVESONG, null);
        removeSongButton.add(songsShowingPanel);
        songsShowingPanel.setVisible(false);

        removeSongButton.addActionListener(e -> {
            songsShowingPanel.setVisible(!showingSongs.get());
            songsShowingPanel.removeAll();
            songsShowingPanel.add(showSongs(songsShowing, emptyPosition, ShowSongsType.REMOVESONG, null));
            showingSongs.set(!showingSongs.get());
        });

        return removeSongPanel;
    }

    public JPanel showSongsPanel() {
        JPanel showSongsPanel = new JPanel();
        showSongsPanel.setLayout(new FlowLayout());

        JButton showSongsBtn = new JButton();
        showSongsBtn.setText("Show Songs");
        showSongsPanel.add(showSongsBtn);
        showSongsBtn.setVisible(true);
        final Song[][] songsShowing = {new Song[Songs.songs.length]};

        int emptyPosition = Utilities.findEmptyPosition(songsShowing[0]);
        if (emptyPosition == -1) {
            int newLength = songsShowing[0].length + 10;
            songsShowing[0] = (Song[]) Utilities.copyArray(songsShowing[0], newLength);
        }

        AtomicBoolean showingSongs = new AtomicBoolean(false);
        JPanel songsShowingPanel = showSongs(songsShowing, emptyPosition, ShowSongsType.PLAYSONG, null);
        showSongsPanel.add(songsShowingPanel);
        songsShowingPanel.setVisible(false);

        showSongsBtn.addActionListener(e -> {
            songsShowingPanel.setVisible(!showingSongs.get());
            songsShowingPanel.removeAll();
            songsShowingPanel.add(showSongs(songsShowing, emptyPosition, ShowSongsType.PLAYSONG, null));
            showingSongs.set(!showingSongs.get());
        });

        return showSongsPanel;
    }

    public JPanel showSongs(Song[][] songsShowing, int emptyPosition, ShowSongsType showSongsType, Playlist playlist) {
        JPanel songsPanel = new JPanel();
        songsPanel.setLayout(new FlowLayout());
        boolean songShowed = false;
        for (Song song : Songs.songs) {
            if (song == null) continue;
            for (Song songShowing : songsShowing[0]) {
                if (songShowing == null) continue;
                if (songShowing.equals(song)) {
                    songShowed = true;
                    break;
                }
            }

            if (songShowed) { continue; }

            JButton songBtn = new JButton();
            songBtn.setText(song.title);
            songBtn.setVisible(true);


            songsShowing[0][emptyPosition] = song;
            songsPanel.add(songBtn);
            songBtn.setVisible(true);

            songBtn.addActionListener(e1 -> {
                if (showSongsType == ShowSongsType.REMOVESONG) {
                    Songs.removeSong(songBtn.getText());
                    songsPanel.remove(songBtn);
                } else if (showSongsType == ShowSongsType.PLAYSONG) {
                    //play song
                } else if (showSongsType == ShowSongsType.ADDSONGTOPLAYLIST) {
                    if (playlist != null) {
                       playlist.addSong(song);
                    }
                }
            });
        }
        return songsPanel;
    }
}
