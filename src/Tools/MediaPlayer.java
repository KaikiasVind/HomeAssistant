package Tools;

import core.IO;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.util.ArrayList;
import java.util.Arrays;

public class MediaPlayer {

    private final MediaPlayerFactory audioPlayerFactor;
    private final AudioPlayerComponent mediaPlayerComponent;

    private final ArrayList<String> currentPlayListFilePaths;

    // Brian -> Fade out

    public MediaPlayer() {
        IO.printSystemMessage("Setting up media player");
        this.audioPlayerFactor = new MediaPlayerFactory();
        this.mediaPlayerComponent = new AudioPlayerComponent();
        this.mediaPlayerComponent.mediaPlayer().events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
            public void finished(MediaPlayer mediaPlayer) {
                playNextItem();
            }

            public void error(MediaPlayer mediaPlayer) {
                shutDown();
            }
        });

        currentPlayListFilePaths = new ArrayList<>();
    }

    /**
     * Start the audio stream
     * @param playListFilePaths - List containing the paths to the files to play as playlist
     */
    public void start(final ArrayList<String> playListFilePaths) {
        IO.printSystemMessage("Starting audio stream");

        // Play the first file from the given play list
        this.mediaPlayerComponent.mediaPlayer().media().play(playListFilePaths.get(0));

        // If the file was the only file just return
        if (playListFilePaths.size() == 1)
            return;

        // Otherwise remove the file path to the currently playing file
        playListFilePaths.remove(0);

        // and add the rest to the current play list
        this.currentPlayListFilePaths.addAll(playListFilePaths);
    }


    /**
     * Pop the next item from the current play list and play it
     */
    public void playNextItem() {
        // If no file path is left return
        if (currentPlayListFilePaths.isEmpty()) {
            IO.printSystemMessage("No other items left");
            return;
        }

        // Else submit the next file path to the media player
        this.mediaPlayerComponent.mediaPlayer().submit(() -> {
            mediaPlayerComponent.mediaPlayer().media().play(currentPlayListFilePaths.get(0));
            currentPlayListFilePaths.remove(0);
        });
    }


    /**
     * Shut down the music player completely
     */
    public void shutDown() {
        mediaPlayerComponent.mediaPlayer().submit(() -> {
            mediaPlayerComponent.mediaPlayer().release();
        });
    }


    public static void main(String [] args) throws InterruptedException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.start(new ArrayList<>(Arrays.asList(args)));
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
