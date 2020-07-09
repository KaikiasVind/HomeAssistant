package core;

public abstract class Addon {

    public final String name;
    public final String[] hotWords;

    /**
     * 
     * @param name
     * @param hotWords
     */
    public Addon(final String name, final String[] hotWords) {
        this.name = name;
        this.hotWords = hotWords;
    }

    /**
     * Take steps to initialize the addon. This is done only once after the addon has been added.
     */
    public abstract void init();

    /**
     * Start the main functionality of the addon
     */
    public abstract void run(String[] parameters);

    /**
     * Change the current run of the Addon with the given addons
     * @param parameters - A list of parameters that is used on the addon
     */
    public abstract void change(String[] parameters);

    /**
     * Stop the run of the Addon and return to the main Loop
     */
    public abstract void stop();

    // ################### INFORMATION ###################
    public abstract boolean isRunning();
    // ################### INFORMATION ###################
}
