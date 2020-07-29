package core;

public abstract class Output {

    // ####################### COLORS ##########################
    private static final String RESET = "\033[0m";  // Text Reset
    private static final String RED = "\033[0;31m";
    // ####################### COLORS ##########################

    /**
     * Print a message to standard output
     * @param content - String containing the message that should be printed to stdout
     */
    public static void print (String content) {
        System.out.println(content);
    }

    /**
     * Print a system message to standard output - This is for debugging purposes and is hidden on normal runs
     * @param systemMessage - String containing the message that should be printed to stdout
     */
    public static void printSystemMessage (String systemMessage) {
        System.out.println(RED + systemMessage + RESET);
    }

}
