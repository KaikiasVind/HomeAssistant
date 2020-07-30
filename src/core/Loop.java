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
            String userInput = IO.in();

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
                IO.printSystemMessage("Executing: " + prioritisedAddons.get(0).name);
                prioritisedAddons.get(0).run(userInput);
            }

        }
    }
}
