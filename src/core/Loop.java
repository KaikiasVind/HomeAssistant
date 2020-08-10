package core;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Loop {

    public static void start (final ArrayList<Addon> addons) {
        IO.printSystemMessage("Program started");
        IO.out("Hi there.");

        while (true) {
            // Read the user input from console
            String userInput = IO.in().toLowerCase();

            // ########################################## Check for core system commands ####################################
            // If the stop word is used, go through every addon and stop it
            if (userInput.contains("stop")) {
                stopEverything(addons);
                // And continue with the next user input
                continue;
            }

            // If the user wants to shut down the home assistant shutdown all running addons, the media player and the whole software
            if (userInput.equals("bye") || userInput.equals("goodbye") || userInput.equals("good night"))
                shutDown(addons);

            // ########################################## Check for media player commands ####################################
            /* The media player commands are embedded as system commands for two reasons:
               - To prevent that every addon that uses the media player has to re-implement the same commands
               - To standardise the usage of media contents for the user across all addons
            */

            // Play the next item in the current media playback
            if (userInput.contains("next") || userInput.contains("skip")) {
                Media.mediaPlayer.playNextItem();
                continue;
            }

            // Play the previous item in the current media playback
            if (userInput.contains("previous") || userInput.contains("last")) {
                Media.mediaPlayer.playPreviousItem();
                continue;
            }

            // Pause the current media playback
            if (userInput.contains("pause") || userInput.contains("wait") || userInput.contains("silence")) {
                Media.mediaPlayer.pausePlayback();
                continue;
            }

            // Un-pause the current media playback
            if (userInput.contains("continue") || userInput.contains("go on")) {
                Media.mediaPlayer.unpausePlayback();
                continue;
            }

            // Lower the media playback volume
            if (userInput.contains("lower volume")) {
                Media.mediaPlayer.lowerVolumeALittle();
                continue;
            }

            // Raise the media playback volume
            if (userInput.contains("raise volume")) {
                Media.mediaPlayer.raiseVolumeALittle();
                continue;
            }

            // ########################################## Check for Addon commands ####################################
            // Go through the hot words of all addons and prioritise all addons by the matches
            ArrayList<Addon> prioritisedAddons = Utils.prioritiseAddons(Utils.parseUserInput(userInput), addons);

            // If the user input didn't match up with any addon, skip the user input
            if (prioritisedAddons.isEmpty()) {
                IO.printSystemMessage("No match with any addon hot-word");
                IO.out("Sorry, I didn't catch that.");
                continue;
            }

            // Check whether more than one addon has the same priority
            boolean isMoreThanOneAddonPriority = prioritisedAddons.size() > 1;

            // If the user input matched more than one addon, ask the user which addon should be used
            if (isMoreThanOneAddonPriority) {
                IO.printSystemMessage("More than one addon with highest priority.");
                IO.out("I don't know exactly what you mean, could you give me a hint?");

                // Get all hot words from the prioritised addons
                ArrayList<String[]> addonHotWords = (ArrayList<String[]>) prioritisedAddons.stream().map(addon -> addon.hotWords).collect(Collectors.toList());

//                UserInteraction.getUserAnswerToQuestion("Which one do you mean?" + addonNames.toString(), addonNames

            } else {

                // Run the addon with the highst priority
                boolean runningAddonSucceeded = runAddon(prioritisedAddons.get(0), userInput);

                // If running the addon didn't succeed, gradually try every other addon that was prioritised.
                if (!runningAddonSucceeded) {
                    for (Addon addon : prioritisedAddons) {
                        boolean runningAlternativeAddonSucceeded = runAddon(addon, userInput);

                        // If the other tested addon worked continue the loop because the the correct addon has been found
                        if (runningAlternativeAddonSucceeded)
                            continue;
                    }
                }
            }

        }
    }


    /**
     * Run the given addon with the given user input and return whether it was successfull or not
     * @param addon - The Addon that is to be run
     * @param userInput - The user input the addon is to be run with
     * @return - Boolean whether the addon has successfully been run or not
     */
    private static boolean runAddon(final Addon addon, final String userInput) {
        // If the spoken to addon is already running, send a signal for additional user input instead of starting the addon
        IO.printSystemMessage("Starting: " + addon.name);
        return addon.reactTo(userInput);
    }

    /**
     * Stop every addon and additionally and stop if still running the media playback
     * @param addons - List of installed addons
     */
    private static void stopEverything(final ArrayList<Addon> addons) {

        // If the stop word is used, go through every addon and stop it
        for (Addon addon : addons) {
            if (addon.isRunning())
                addon.stop();
        }

        // In case any addon did not stop the media playback, stop it manually
        if (Media.mediaPlayer.isRunning())
            Media.mediaPlayer.stopPlayback();
    }


    /**
     * Shutdown all running addons, the media player and the whole software
     * @param addons - List of installed addons
     */
    private static void shutDown(final ArrayList<Addon> addons) {
        stopEverything(addons);
        Media.mediaPlayer.shutDown();
        IO.out("Bye!!");
        System.exit(0);
    }
}
