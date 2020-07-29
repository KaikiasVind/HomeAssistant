package core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public abstract class AddonManager {

    // private final static String addonPath = "/home/numelen/Documents/Programming/Java/homeassistant/out/production/homeassistant/addons/";
    private final static String addonPath = "addons/";

    public static void getAddons() {

        File file = new File(addonPath + "InstantAnswers.jar");
        try {
            JarFile jarFile = new JarFile(file);

            URLClassLoader ucl = new URLClassLoader(new URL[] { file.toURI().toURL()});

            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                String entryName = entries.nextElement().getName();

                if (!entryName.endsWith(".class") || entryName.equals("Addon.class"))
                    continue;

                Class<?> foundClass = ucl.loadClass(entryName.replace("/", ".").replace(".class", ""));
                Addon a = (Addon) foundClass.getDeclaredConstructor().newInstance();
                a.init();
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
//        System.out.println(System.getProperty("user.dir"));
//        System.exit(0);

//        File[] addonFiles = new File(addonPath).listFiles();
//        ArrayList<String> addonFileNames = new ArrayList<String>() ;
//
//        if (addonFiles == null) {
//            System.out.println("I found no addons.");
//            return;
//        }
//
//        for (File file : addonFiles) {
//            if (file.isFile())
//                addonFileNames.add(file.getName().replace(".class", ""));
//        }
//
//        for (String string : addonFileNames)
//            System.out.println(string);
//
//        System.exit(0);

//        try {
//            URL[] urls = new URL[] { file.toURI().toURL() };

//            for (URL url : urls) {
//                System.out.println(url.toString());
//                Class<?> addonClass = Class.forName("addons.InstantAnswers");
//                Addon foundAddon = (Addon) addonClass.getConstructor().newInstance();
//                foundAddon.init();
//            }


//        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            e.printStackTrace();
//        }


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
