package core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class AddonManager {

    // private final static String addonPath = "/home/numelen/Documents/Programming/Java/homeassistant/out/production/homeassistant/addons/";
    private final static String addonPath = "addons/";

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

                    if (entryName.startsWith("Addon") && !entryName.equals("Addon.class")) {
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
}
