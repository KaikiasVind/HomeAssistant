package core;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Utils {

    /**
     *
     * @param userInput
     * @return
     */
    public static String[] parseUserInput(final String userInput) {
        return userInput.split(" ");
    }

    /**
     *
     * @param parsedUserInput
     * @return
     */
    public static ArrayList<Addon> prioritiseAddons(final String[] parsedUserInput, final ArrayList<Addon> addons) {
        HashMap<Addon, Integer> addonsWithPrios = new HashMap<>();

        // If no addons have been given, return an empty list
        if (addons.isEmpty())
            return new ArrayList<>();

        // Find the priorities for each addon based on the overlap of the user input and the addon's hot words
        for (Addon addon : addons) {
            int prio = 0;
            for (String word : parsedUserInput) {
                for (String hotWord : addon.hotWords) {
                    if (word.toLowerCase().equals(hotWord.toLowerCase())) {
                        prio++;
                    }
                }
            }
            addonsWithPrios.put(addon, prio);
        }

        // Grab the highest prio that has been calculated
        int highestFoundPrio = Collections.max(addonsWithPrios.values());

        // If no word of the user input matched with any hot-word of any addon return an empty list
        if (highestFoundPrio == 0)
            return new ArrayList<>();

        // And find all addons that share this highest priority
        ArrayList<Addon> prioritisedAddons = (ArrayList<Addon>) addonsWithPrios.entrySet().stream()
                .filter(entry -> entry.getValue() == highestFoundPrio)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());

        return prioritisedAddons;
    }
}
