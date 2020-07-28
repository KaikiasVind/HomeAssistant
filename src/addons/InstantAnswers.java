package addons;

import core.Addon;

public class InstantAnswers implements Addon {

    public static final String name = "InstantAnswers";
    public static final String[] hotWords = new String[] {"define"};

    public InstantAnswers() {};

    @Override
    public void init() {
        System.out.println("Initializing.");
    }

    @Override
    public void run(String[] parameters) {
        System.out.println("Running.");
    }

    @Override
    public void change(String[] parameters) {
        System.out.println("Running.");
    }

    @Override
    public void stop() {
        System.out.println("Stopping.");
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}

