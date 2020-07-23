package core;

import addons.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public abstract class AddonManager {

    private final static String addonPath = "/home/numelen/Documents/Programming/Java/homeassistant/out/production/homeassistant/addons/InstantAnswers.class";

    public static void getAddons() {

        File file = new File(addonPath);

        try {
            URL url = file.toURI().toURL();
            URL[] urls = new URL[] { url };

            ClassLoader cl = new URLClassLoader(urls);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


//        Addon [] foundAddons;
//
//        File [] addonFiles = new File(addonPath).listFiles();
//
//        for (File file : addonFiles) {
//            URLClassLoader urlClassLoader = null;
//            try {
//                urlClassLoader = new URLClassLoader(  new URL[] { file.toURI().toURL() });
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            if (file.getName().endsWith(".class")) {
//                String className = file.getName().replace("/", ".").replace(".class", "");
//                System.out.println(className);
//                Class<?> foundClass;
//                try {
//                    foundClass = urlClassLoader.loadClass(file.getName().replace("/", ".").replace(".class", ""));
//                    Addon a = (Addon) foundClass.getDeclaredConstructor().newInstance();
//                    System.out.println(a.name);
//
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
    }


    public static void gatherAddons() {

        File[] addonFiles = new File(addonPath).listFiles();


        if (addonFiles == null) {
            System.out.println("No such directory: " + addonPath);
        } else {
            for (File file : addonFiles) {
                System.out.println(file.getName());
                if (file.isFile()) {
                    String className = file.getName().replace("/",".").replace(".class", "");
                    System.out.println("Found: " + className);
                    try {
                        Addon addon = (Addon) Class.forName(className).getDeclaredConstructor().newInstance();

                        System.out.println(addon.name);
                    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
