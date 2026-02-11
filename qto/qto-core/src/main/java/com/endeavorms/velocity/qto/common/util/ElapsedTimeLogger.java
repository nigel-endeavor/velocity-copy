package com.endeavorms.velocity.qto.common.util;

import com.google.common.base.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * @author mmeehan
 * @since 1.11.1
 */
public class ElapsedTimeLogger {

    /** Logger, set to this class but can be overridden. */
    private static final Logger DEFAULT_LOGGER = LoggerFactory.getLogger(ElapsedTimeLogger.class);;

    /** Logger, can be set by user of this class. */
    private Logger logger;

    /** Stopwatch. */
    private Stopwatch stopwatch = createStopwatch();

    /**
     * Constuctor.
     */
    public ElapsedTimeLogger() {
    }

    /**
     * Constructor.
     * @param logger a logger.
     */
    public ElapsedTimeLogger(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Create a started Stopwatch.
     * @return the Stopwatch.
     */
    private Stopwatch createStopwatch() {
        return Stopwatch.createUnstarted();
    }


    /**
     * Start the stop watch if it is not already running.
     */
    public void start() {
        if (!stopwatch.isRunning()) {
            stopwatch.start();
        }
    }


    /**
     * Log elapsed time and reset and start the stopwatch.
     * @param message logger message.
     * @param objects objects to inject into logger message.
     */
    public void lap(final String message, final Object... objects) {
        Logger chosenLogger = (logger == null) ? DEFAULT_LOGGER : logger;
        lap(chosenLogger, message, objects);
    }


    /**
     * Log elapsed time and reset and start the stopwatch.
     * @param logger a Logger.
     * @param message logger message.
     * @param objects objects to inject into logger message.
     */
    public void lap(final Logger logger, final String message, final Object... objects) {
        logElapsed(logger, message, objects);
        stopwatch.reset();
        stopwatch.start();
    }


    /**
     * Log a message and elapsed time in seconds.  Will stop the stopwatch.
     * @param message logger message.
     * @param objects objects to inject into logger message.
     */
    public void logElapsed(final String message, final Object... objects) {
        Logger chosenLogger = (logger == null) ? DEFAULT_LOGGER : logger;
        logElapsed(chosenLogger, message, objects);
    }


    /**
     * Log a message and elapsed time in seconds.  Will stop the stopwatch.
     * @param logger a Logger.
     * @param message logger message.
     * @param objects objects to inject into logger message.
     */
    public void logElapsed(final Logger logger, final String message, final Object... objects) {
        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        Object[] newArray = Arrays.copyOf(objects, objects.length + 1);
        newArray[0] = elapsed;
        System.arraycopy(objects, 0, newArray, 1, objects.length);
        String logMessage = "elapsed = {} : " + message;
        logger.debug(logMessage, newArray);
    }


    public Logger getLogger() {
        return logger;
    }

    public void setLogger(final Logger logger) {
        this.logger = logger;
    }

    /**
     * Builder.
     */
    public static class Builder {

        /** Logger, can be set by user of this class. */
        private Logger logger;

        /** Start flag. */
        private boolean startNow = false;

        /**
         * Constructor.
         */
        public Builder() {
        }

        /**
         * Build the ElapsedTimeLogger.
         * @return an ElapsedTimeLogger.
         */
        public ElapsedTimeLogger build() {
            ElapsedTimeLogger e = new ElapsedTimeLogger();
            if (this.logger != null) {
                e.setLogger(logger);
            }
            if (this.startNow) {
                e.start();
            }
            return e;
        }

        /**
         * Specify the logger.
         * @param logger the logger.
         * @return this ElapsedTimeLoggerBuilder.
         */
        public Builder logger(final Logger logger) {
            this.logger = logger;
            return this;
        }


        /**
         * Start the timer upon creation.
         * @return this ElapsedTimeLoggerBuilder.
         */
        public Builder startNow() {
            this.startNow = true;
            return this;
        }

    }

}
