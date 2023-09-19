package utils;

import core.config.Config;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.FileHandler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.LogManager;

/**
 * Class to log information to console or a file
 * @author Jonathan Salisbury
 */

public class GERGLogger {
    
    /* COLORS */
    private static final String RESET = "\033[0m";
    private static final String RED = "\033[31m";
    private static final String YELLOW = "\033[33m";
    private static final String GREEN = "\033[32m";
    private static final String BLUE = "\033[36m";  
    
    
    private static final int MAX_RECORD_LENGTH = 1000;

    /**
     * Method that initializes the Logger with the config file info.
     */
    public static void init() {
        init(Config.getStringValue("logger.path"));
    }

    /**
     * Method that initializes the Logger with the given file.
     * @param path
     */
    public static void init(String path) {
        LogManager.getLogManager().reset();
        Logger LOGGER = Logger.getLogger("");
        try {    
            Handler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new GERGConsoleFormatter());
            consoleHandler.setLevel(Level.ALL);
            LOGGER.addHandler(consoleHandler);

            if(Config.getBoolValue("logger.save_to_file")) {
                DateFormat df = new SimpleDateFormat("dd-MM_hh-mm-ss");
                Handler fileHandler = new FileHandler(path + "/" + df.format(new Date(System.currentTimeMillis())) + ".log");
                fileHandler.setFormatter(new GERGFileFormatter());
                fileHandler.setLevel(Level.ALL);
                LOGGER.addHandler(fileHandler);
            }
            LOGGER.setLevel(Level.ALL);
            LOGGER = Logger.getLogger(GERGLogger.class.getSimpleName());
            LOGGER.config("Logger Initialized");
        } catch (IOException | SecurityException ex) {
            Logger.getLogger("LOGGER").severe(ex.toString());
        }
    }

    /**
     * Util class for formatting the messages in the file.
     */
    private static class GERGFileFormatter extends Formatter {
        
        private static final DateFormat df = new SimpleDateFormat("hh:mm:ss");

        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(MAX_RECORD_LENGTH);
            Level level = record.getLevel();
            builder.append("[").append(df.format(new Date(record.getMillis()))).append("]");
            builder.append("[").append(record.getLoggerName().toUpperCase()).append("]");
            builder.append("[").append(level.getName()).append("] ");
            builder.append(formatMessage(record));
            return builder.toString() + "\n";        
        }
    
    }

    /**
     * Util class for formatting the messages in the console.
     */
    private static class GERGConsoleFormatter extends Formatter {
        
        private static final DateFormat df = new SimpleDateFormat("hh:mm:ss");

        @Override
        public String format(LogRecord record) {
            StringBuilder builder = new StringBuilder(MAX_RECORD_LENGTH);
            Level level = record.getLevel();
            if(level.equals(Level.SEVERE)) {
                builder.append(RED);
            } else if(level.equals(Level.WARNING)) {
                builder.append(YELLOW);
            } else if(level.equals(Level.INFO)) {
                builder.append(GREEN);
            } else if(level.equals(Level.CONFIG)) {
                builder.append(BLUE);
            }
            builder.append("[").append(df.format(new Date(record.getMillis()))).append("]");
            builder.append("[").append(record.getLoggerName().toUpperCase()).append("]");
            builder.append("[").append(level.getName()).append("] ");
            builder.append(formatMessage(record)).append(RESET);
            return builder.toString() + "\n";        
        }
    
    }
}
