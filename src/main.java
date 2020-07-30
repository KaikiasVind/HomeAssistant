import core.Addon;
import core.IO;
import core.Loop;
import core.AddonManager;

import java.util.ArrayList;

public class main {

    public static void main (String[] arguments) {
        IO.setInputOutputType(IO.IOTYPE.CONSOLE, IO.IOTYPE.CONSOLE);

        // Grab all addons from the addon folder
        ArrayList<Addon> addons = AddonManager.getAddons();

        // and initialize them
        AddonManager.initializeAddons(addons);

        // Start the system loop of user input and system reaction
        Loop.start(addons);
    }
}
