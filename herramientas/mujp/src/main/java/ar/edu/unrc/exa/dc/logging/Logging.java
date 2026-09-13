package ar.edu.unrc.exa.dc.logging;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.*;

/**
 * This class takes care of both console and file logging.
 * Methods in this class will all return a Logger with both a console logger and a file logger
 * the file logger will save logs to a file named {@code logs/<class-name>.log}
 * @see {@link java.util.logging.Logger}
 */
public final class Logging {

    private final static String LOGS_FOLDER = "logs";

    /**
     * This class defines the different logging levels to be used with {@link ar.edu.unrc.exa.dc.logging.Logging}
     */
    public enum LoggingLevel {
        /**
         * Disables logging
         */
        OFF {
            @Override
            public Level getLoggerLevel() {
                return Level.OFF;
            }
        },
        /**
         * Only log information level messages
         */
        INFO {
            @Override
            public Level getLoggerLevel() {
                return Level.INFO;
            }
        },
        /**
         * Logs everything
         */
        FINE {
            @Override
            public Level getLoggerLevel() {
                return Level.FINE;
            }
        };
        public abstract Level getLoggerLevel();
    }

    /**
     * Returns a {@code Logger} for a particular class while logging everything
     * @param forClass : the class for which the logger is created
     * @return a logger for {@code forClass} using {@link ar.edu.unrc.exa.dc.logging.Logging.LoggingLevel#FINE} level
     * @see {@link java.util.logging.Logger}
     */
    public static Logger getLogger(Class<?> forClass) {return getLogger(forClass, LoggingLevel.FINE); }

    /**
     * Returns a {@code Logger} for a particular class and a particular logging level
     * @param forClass : the class for which the logger is created
     * @param loggingLevel : the level of logging to use
     * @return a logger for {@code forClass} using {@code loggingLevel} level
     * @see {@link java.util.logging.Logger}
     */
    public static Logger getLogger(Class<?> forClass, LoggingLevel loggingLevel) {
        return getLogger(forClass, loggingLevel, loggingLevel);
    }

    /**
     * Returns a {@code Logger} for a particular class and a particular logging level
     * @param forClass : the class for which the logger is created
     * @param consoleLoggingLevel : the level of logging to use for the console logger
     * @param fileLoggingLevel : the level of logging to use for the file logger
     * @return a logger for {@code forClass} using {@code consoleLoggingLevel} level for the console logger
     *         and {@code fileLoggingLevel} for the file logger.
     * @see {@link java.util.logging.Logger}
     */
    public static Logger getLogger(Class<?> forClass, LoggingLevel consoleLoggingLevel, LoggingLevel fileLoggingLevel) {
        Logger logger = Logger.getLogger(forClass.getName());
        logger.setLevel(Level.FINE);
        final Path logsFolder = Paths.get("", LOGS_FOLDER);
        try{
            Files.createDirectories(logsFolder);
            Handler[] handlers = logger.getHandlers();
            Arrays.stream(handlers).forEach(logger::removeHandler);
            CoolConsoleHandler consoleHandler = new CoolConsoleHandler();
            FileHandler fileHandler = new FileHandler(Paths.get(logsFolder.toString(), forClass.getName() +  ".log").toString());
            fileHandler.setLevel(fileLoggingLevel.getLoggerLevel());
            fileHandler.setFormatter(new SimpleFormatter());
            consoleHandler.setLevel(consoleLoggingLevel.getLoggerLevel());
            consoleHandler.setFormatter(new JustMessageFormatter());
            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch(IOException exception){
            logger.log(Level.SEVERE, "Error occur in FileHandler.", exception);
        }
        return logger;
    }

    private static class CoolConsoleHandler extends StreamHandler {

        private final ConsoleHandler stderrHandler = new ConsoleHandler();

        public CoolConsoleHandler() {
            super(System.out, new SimpleFormatter());
        }

        @Override
        public void publish(LogRecord record) {
            if (record.getLevel().intValue() >= getLevel().intValue()) {
                super.publish(record);
                super.flush();
            } else if (goesToErr(record.getLevel().intValue())) {
                stderrHandler.publish(record);
                stderrHandler.flush();
            }
        }

        private boolean goesToErr(int level) {
            return level == Level.SEVERE.intValue() || level == Level.WARNING.intValue();
        }

    }

    private static class JustMessageFormatter extends SimpleFormatter {

        @Override
        public String format(LogRecord record) {
            if (record.getLevel().intValue() == Level.WARNING.intValue() || record.getLevel().intValue() == Level.SEVERE.intValue()) {
                return super.format(record);
            } else {
                return record.getMessage() + "\n";
            }
        }

    }

}
