package core;

import java.util.Scanner;

public abstract class IO {

    public enum IOTYPE {
        VOICE, CONSOLE
    }

    // #################################################################################################################
    // ################################################### OUTPUT ######################################################
    // #################################################################################################################

    private static IOTYPE currentOutputType = IOTYPE.CONSOLE;
    private static boolean useDebuggingMode = false;

    // ####################### COLORS ##########################
    private static final String RED = "\033[0;31m";
    private static final String RESET = "\033[0m"; // Default color
    // ####################### PRINT TO CONSOLE ##########################

    public static void out(final String content) {
        if (currentOutputType == IOTYPE.CONSOLE)
            print(content);
    }

    /**
     * Print a system message to standard output - This is for debugging purposes and is hidden on normal runs
     *
     * @param systemMessage - String containing the message that should be printed to stdout
     */
    public static void printSystemMessage(final String systemMessage) {
        if (useDebuggingMode)
            print(RED + systemMessage + RESET);
    }

    public static void printEmptyLines(final int number) {
        for (int i = 0; i < number; i++)
            System.out.println();
    }

    // ############################################# UNIFIED OUTPUT ###################################################

    /**
     * Output a unified acceptance message - Usable from anywhere
     */
    public static void outAcceptMessage() {
        out("OK");
    }



    // ################################################ BACKGROUND ####################################################
    /**
     * Print a message to standard output
     *
     * @param content - String containing the message that should be printed to stdout
     */
    private static void print(final String content) {
        System.out.println(content);
    }

    // #################################################################################################################
    // ################################################### INPUT #######################################################
    // #################################################################################################################

    private static IOTYPE currentInputType = IOTYPE.CONSOLE;

    private final static Scanner consoleInputScanner = new Scanner(System.in);

    /**
     * Gather user input from the current selected input source
     * @return - Raw user input as String
     */
    public static String in() {
        if (currentInputType == IOTYPE.CONSOLE)
            return readUserInputFromConsole();
        return "";
    }

    /**
     * @return
     */
    private static String readUserInputFromConsole() {
        return consoleInputScanner.nextLine();
    }

    // #################################################################################################################
    // ################################################### MISC ########################################################
    // #################################################################################################################

    /**
     * Set the input and output type that should be used
     * @param inputType - The way the user interacts with the program
     * @param outputType - The way the program interacts with the user
     */
    public static void setInputOutputType(IOTYPE inputType, IOTYPE outputType, boolean isDebuggingModeActive) {
        currentInputType = inputType;
        currentOutputType = outputType;
        useDebuggingMode = isDebuggingModeActive;
    }

}
