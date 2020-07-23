package core;

public interface Addon {

    String name = null;
    String[] hotWords = new String[0];

    /**
     * Take steps to initialize the addon. This is done only once after the addon has been added.
     */
    void init();

    /**
     * Start the main functionality of the addon
     */
    void run(String[] parameters);

    /**
     * Change the current run of the Addon with the given addons
     * @param parameters - A list of parameters that is used on the addon
     */
    void change(String[] parameters);

    /**
     * Stop the run of the Addon and return to the main Loop
     */
    void stop();

    // ################### INFORMATION ###################
    boolean isRunning();
    // ################### INFORMATION ###################
}
