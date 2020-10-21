import core.Addon;
import core.IO;
import core.Loop;
import core.AddonManager;

import java.util.ArrayList;

public class main {


    public static void main (String[] arguments) {

        IO.setInputOutputType(IO.IOTYPE.CONSOLE, IO.IOTYPE.CONSOLE, true);

        // Grab all addons from the addon folder
        ArrayList<Addon> addons = AddonManager.getAddons();

        IO.printSystemMessage("###################### CONFIG #######################");
        IO.printSystemMessage("...");
        IO.printEmptyLines(1);

        IO.printSystemMessage("###################### ADDONS #######################");
        if (addons.isEmpty()) {
            IO.printSystemMessage("No addons found.");
        } else {
            IO.printSystemMessage(addons.size() + " addon(s) found:");
            for (Addon addon : addons)
                IO.printSystemMessage(addon.name);
        }
        IO.printEmptyLines(1);

        // and initialize them
        AddonManager.initializeAddons(addons);

        // Start the system loop of user input and system reaction
        Loop.start(addons);
    }
}
