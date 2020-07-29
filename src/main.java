import core.Addon;
import core.Loop;
import core.AddonManager;

import java.util.ArrayList;

public class main {

    public static void main (String[] arguments) {
        ArrayList<Addon> addons = AddonManager.getAddons();

        for (Addon addon : addons) {
            System.out.println(addon.name);
        }
    }
}
