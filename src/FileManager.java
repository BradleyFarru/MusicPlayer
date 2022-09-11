import java.io.*;
import java.nio.file.*;

public class FileManager implements Serializable {
    private static void createDirectory(String directoryName) {
        File[] listOfFiles = new File(System.getProperty("user.dir")).listFiles();
        assert listOfFiles != null;
        String[] listOfDirectories = new String[listOfFiles.length + 1];

        for (File file : listOfFiles) {
            if (!file.isFile()) {
                int emptyPosition = Utilities.findEmptyPosition(listOfDirectories);
                listOfDirectories[emptyPosition] = file.toString();
            }
        }

        boolean songsDirExists = false;
        for (String directory : listOfDirectories) {
            if (directory != null) {
                if (directory.equals(directoryName)) {
                    songsDirExists = true;
                    break;
                }
            }
        }

        if (!songsDirExists) {
            boolean songsDir = new File(directoryName).mkdir();
            if (songsDir) {
                System.out.println("Successfully created " + directoryName + " directory!");
            }
        }
    }

    public static void createFile(String directoryName, String fileName, Song song, Playlist playlist, ObjectType objectType) {
        try {
            createDirectory(directoryName);

            Path path = Paths.get(System.getProperty("user.dir"));
            File newFile = new File(path + "/" + directoryName + "/" + fileName);
            boolean createFile = newFile.createNewFile();
            if (createFile) {
                if (objectType == ObjectType.PLAYLIST) {
                    saveFile(newFile.getPath(), playlist, null);
                } else if (objectType == ObjectType.SONG){
                    saveFile(newFile.getPath(), null, song);
                }
                System.out.println("File created successfully!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    enum ObjectType {
        SONG,
        PLAYLIST
    }

    public static void loadSavedFiles(ObjectType objectType) {
        String directoryName = "";
        if (objectType == ObjectType.SONG) {
            directoryName = "songs";
        } else if (objectType == ObjectType.PLAYLIST) {
            directoryName = "playlists";
        }

        File dir = new File(System.getProperty("user.dir") + "/" + directoryName);
        File[] files = dir.listFiles();
        Object object = null;
        if (files == null) return;
        for (File file : files) {
            if (file == null) continue;
            String fileExtension = "";

            int i = file.getPath().lastIndexOf('.');
            if (i > 0) {
                fileExtension = file.getPath().substring(i+1);
            }

            if (fileExtension.equals("DS_Store")) {
                continue;
            }

            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                object = ois.readObject();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if (object != null) {
                try {
                    if (objectType == ObjectType.SONG) {
                        Song mySong = (Song) object;

                        for (Song song : Songs.songs) {
                            if (song != null) {
                                if (song.title.equals(mySong.title) && song.artist.equals(mySong.artist) && song.filePath.equals(mySong.filePath)) {
                                    break;
                                }
                            }
                        }

                        Songs.createSong(mySong.title, mySong.artist, mySong.filePath);

                    } else if (objectType == ObjectType.PLAYLIST) {
                        Playlist myPlaylist = (Playlist) object;
                        for (Playlist playlist : Playlists.playlists) {
                            if (playlist != null) {
                                if (playlist.name.equals(myPlaylist.name)) {
                                    break;
                                }
                            }
                        }

                        String[] arrayOfSongTitles = new String[10];
                        for (Song song : myPlaylist.songs) {
                            if (song != null) {
                                int emptyPosition = Utilities.findEmptyPosition(arrayOfSongTitles);
                                if (emptyPosition == -1) {
                                    int newLength = arrayOfSongTitles.length + 10;
                                    arrayOfSongTitles = (String[]) Utilities.copyArray(arrayOfSongTitles, newLength);
                                }
                                arrayOfSongTitles[emptyPosition] = song.title;
                            }
                        }
                        Playlists.createPlaylist(myPlaylist.name, arrayOfSongTitles);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveFile(String filePath, Playlist playlist, Song song) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.flush();
            if (song == null) {
                oos.writeObject(playlist);
            } else {
                oos.writeObject(song);
            }

            if (filePath.equals("null")) {
               FileOutputStream fos1 = new FileOutputStream(System.getProperty("user.dir") + "/playlists/" + playlist.name);
               ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
               oos1.flush();
               oos1.writeObject(playlist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeFile(String filePath) {
        try {
            Path path = Paths.get(System.getProperty("user.dir") + filePath);
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
