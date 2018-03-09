package util;

import java.util.logging.Level;

public class Logger {

    private Logger() {
        //Empty private constructor to prevent intellij from automatically creating a public one.
        //Preventing a public constructor, since this is a utility class (using static methods only).
    }

    public static void log (String msg) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());
        logger.log(Level.ALL, msg);
    }

    public static void log (Exception x) {
        java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Logger.class.getName());
        logger.log(Level.ALL, x.getMessage());
    }
}