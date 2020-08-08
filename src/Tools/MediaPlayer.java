package Tools;

import core.IO;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MediaPlayer {

    private final MediaPlayerFactory audioPlayerFactor;
    private final AudioPlayerComponent mediaPlayerComponent;

    private final ArrayList<String> currentPlayListFilePaths;
    private int currentIndex;

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

        this.currentPlayListFilePaths = new ArrayList<>();
        this.currentIndex = 0;
    }


    /**
     * Start the audio stream
     * @param playListFilePaths - List containing the paths to the files to play as playlist
     */
    public void start(final ArrayList<String> playListFilePaths) {
        IO.printSystemMessage("Starting audio stream");

        // Reset the current item index
        this.currentIndex = 0;

        // Add all given items to the current play list
        this.currentPlayListFilePaths.addAll(playListFilePaths);

        // And play the first item
        this.submitNewItemIndexToVLCPlayer(0);
    }


    /**
     * Get the next item from the current playlist and play it
     */
    public void playNextItem() {
        IO.printSystemMessage("Playing next item");

        // If the current item has been the last one in the list, return to the beginning of the list
        if (currentIndex == this.currentPlayListFilePaths.size() - 1) {
            this.currentIndex = 0;
        } else {
            // Else increase the current item index
            this.currentIndex += 1;
        }

        // And submit the next file path to the media player
        this.submitNewItemIndexToVLCPlayer(this.currentIndex);
    }


    /**
     * Get the previous item from the current playlist and play lit
     */
    public void playPreviousItem() {
        IO.printSystemMessage("Playing previous item");

        // If the current item has been the first one in the list and was not the only one, go to the end of the list
        if (this.currentIndex == 0 && this.currentPlayListFilePaths.size() > 1) {
            this.currentIndex = this.currentPlayListFilePaths.size() - 1;
        } else {
            // Else decrease the current item index
            this.currentIndex -= 1;
        }

        // And submit the next file path to the media player
        this.submitNewItemIndexToVLCPlayer(this.currentIndex);
    }


    /**
     * Pause the current media playback
     */
    public void pausePlayback() {
        IO.printSystemMessage("Pausing media playback");
        mediaPlayerComponent.mediaPlayer().controls().pause();
    }


    /**
     * Unpause the current media playback
     */
    public void unpausePlayback() {
        IO.printSystemMessage("Unpausing media playback");
        mediaPlayerComponent.mediaPlayer().controls().play();
    }


    /**
     * Stop the media playback
     */
    public void stopPlayback() {
        IO.printSystemMessage("Stopping media playback");
        mediaPlayerComponent.mediaPlayer().controls().stop();
    }


    /**
     * Changes the current playback volume by the given value
     * @param value Positive or negative int that is added to the current playback volume
     */
    public void alterVolume(final int value) {
        IO.printSystemMessage("Altering volume");

        // Get the current volume
        int volume = mediaPlayerComponent.mediaPlayer().audio().volume();

        // Add the new volume to the current volume and limit it to [0,100]
        // The addition can also handle negative volume changes well
        volume = Math.min(Math.max(volume + value, 0), 100);

        // And set the new volume for the player
        mediaPlayerComponent.mediaPlayer().audio().setVolume(volume);
    }


    /**
     * Shut down the music player completely
     */
    public void shutDown() {
        IO.printSystemMessage("Shutting down media player");
        mediaPlayerComponent.mediaPlayer().submit(() -> {
            mediaPlayerComponent.mediaPlayer().release();
        });
    }


    /**
     *
     * @param itemIndex
     */
    private void submitNewItemIndexToVLCPlayer(final int itemIndex) {
        this.mediaPlayerComponent.mediaPlayer().submit(() -> {
            mediaPlayerComponent.mediaPlayer().media().play(currentPlayListFilePaths.get(itemIndex));
        });
    }


    public static void main(String [] args) throws InterruptedException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        ArrayList<String> bla = new ArrayList<>();
        bla.add("C:\\Users\\Kademuni\\Nextcloud\\Music\\Maribou State - 'Midas' feat. Holly Walker.wav");
        bla.add("C:\\Users\\Kademuni\\Nextcloud\\Music\\Michael Kiwanuka - You Ain’t The Problem (Official Video).wav");
        bla.add("C:\\Users\\Kademuni\\Nextcloud\\Music\\Sunday Joyride (Upbeat Lo-fi Hip Hop Mix).wav");
        bla.add("C:\\Users\\Kademuni\\Nextcloud\\Music\\The Teskey Brothers - So Caught Up (Official Video).wav");
        mediaPlayer.start(bla);
        Thread.sleep(2000);
        mediaPlayer.alterVolume(-500);
        Thread.sleep(2000);
        mediaPlayer.alterVolume(100);
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
