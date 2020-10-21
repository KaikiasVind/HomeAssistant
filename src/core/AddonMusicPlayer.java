package core;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class AddonMusicPlayer extends Addon {

    private final String localMusicFolder;
    private ArrayList<String> localMusicFiles;
    private boolean isRunning = false;

    public AddonMusicPlayer() {
        super("MusicPlayer", new String[]{"play", "my", "music", "shuffle", "next", "skip", "previous", "song"});
        localMusicFolder = "C:\\Users\\Kademuni\\Nextcloud\\Music";
    }

    @Override
    public void init() {

        // Get all files from local music folder
        File[] musicFiles = new File(this.localMusicFolder).listFiles();

        // If no music file has been found just return
        if (musicFiles == null)
            return;

        // Otherwise get all file names from files and add them to the list
        localMusicFiles = Arrays.stream(musicFiles)
                .map(File::getAbsolutePath)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public boolean reactTo(String userInput) {
        if (userInput.contains("music")) {
            Media.mediaPlayer.start(this.localMusicFiles);
            this.isRunning = true;
            return true;
        }

        return false;
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }
}
