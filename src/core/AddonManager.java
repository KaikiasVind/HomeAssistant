package core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class AddonManager {

    private final static String addonPath = Paths.get("", "addons").toAbsolutePath().toString();

    /**
     *
     * @return
     */
    public static ArrayList<Addon> getAddons() {

        ArrayList<Addon> foundAddons = new ArrayList<>();

        File[] addonFiles = new File(addonPath).listFiles();

        // If no addons have been found just return an empty list
        if (addonFiles == null)
            return foundAddons;

        // Otherwise go through every found jar file and cast the found main classes to Addon
        for (File addonFile : addonFiles) {
            try {
                JarFile jarFile = new JarFile(addonFile);

                URLClassLoader ucl = new URLClassLoader(new URL[]{addonFile.toURI().toURL()});

                Enumeration<JarEntry> entries = jarFile.entries();

                while (entries.hasMoreElements()) {
                    String entryName = entries.nextElement().getName();

                    if (entryName.contains("Addon") && !entryName.equals("Addon.class")) {
                        Class<?> foundClass = ucl.loadClass(entryName.replace("/", ".").replace(".class", ""));
                        Addon foundAddon = (Addon) foundClass.getDeclaredConstructor().newInstance();
                        foundAddons.add(foundAddon);
                    }
                }
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return foundAddons;
    }


    /**
     *
     * @param addons
     */
    public static void initializeAddons(final ArrayList<Addon> addons) {
        for (Addon addon : addons)
            addon.init();
    }


    /**
     * Gather all hot words from the given addons and put them in a usable hash map
     * @param addons - List of addons
     * @return - HashMap containing the hotword list and
     */
    public static HashMap<String[], Addon> getAddonHotWords(final ArrayList<Addon> addons) {
        HashMap<String[], Addon> hotWordsWithAssociatedAddons = new HashMap<>();

        // For every addon get the corresponding hot-word-list
        for (Addon addon : addons)
            hotWordsWithAssociatedAddons.put(addon.hotWords, addon);

        return hotWordsWithAssociatedAddons;
    }
}
