package core;

import java.io.File;
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
    public void run(String userInput) {
        if (userInput.contains("my music"))
            Media.mediaPlayer.start(this.localMusicFiles);
            this.isRunning = true;
    }

    @Override
    public void change(String userInput) {
        if (userInput.contains("next") || userInput.contains("skip"))
            Media.mediaPlayer.playNextItem();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return this.isRunning;
    }
}
