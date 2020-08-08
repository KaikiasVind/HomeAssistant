package core;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Loop {

    public static void start (final ArrayList<Addon> addons) {
        IO.printSystemMessage("Program started");
        IO.out("Hi there.");

        while (true) {
            // Read the user input from console
            String userInput = IO.in().toLowerCase();

            // ########################## Check for core system commands ##########################

            // If the stop word is used, go through every addon and stop it
            if (userInput.contains("stop")) {
                for (Addon addon : addons) {
                    if (addon.isRunning())
                        addon.stop();
                }
            }

            // ########################## Check for core system commands ##########################

            ArrayList<Addon> prioritisedAddons = Utils.prioritiseAddons(Utils.parseUserInput(userInput), addons);

            // If the user input didn't match up with any addon, skip the user input
            if (prioritisedAddons.isEmpty()) {
                IO.printSystemMessage("No match with any addon hot-word");
                IO.out("Sorry, I didn't catch that.");
                continue;
            }

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

                        // If the other tested addon worked break the loop because the the correct addon has been found
                        if (runningAlternativeAddonSucceeded)
                            break;
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
        if (addon.isRunning()) {
            IO.printSystemMessage("Changing: " + addon.name);
            return addon.change(userInput);
        } else {
            IO.printSystemMessage("Starting: " + addon.name);
            return addon.run(userInput);
        }
    }
}
