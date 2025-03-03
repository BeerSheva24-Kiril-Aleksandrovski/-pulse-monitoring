package telran.monitoring.logging;

import java.util.HashMap;
import java.util.logging.*;

public class LoggerStandard implements Logger {
    String defaultValue = "info";
    java.util.logging.Logger logger;
    static HashMap<String, String> levelsMap = new HashMap<>() {
        {
            put("debug", "config");
            put("trace", "fine");
        }
    };

    public LoggerStandard(String loggerName) {
        logger = LogManager.getLogManager().getLogger(loggerName);
        String level = System.getenv("LOGGER_LEVEL");
        String javaLevel = levelsMap.get(level);
        if (javaLevel != null) {
            level = javaLevel;
        }
        logger.setLevel(Level.parse(level.toUpperCase()));
    }

    @Override
    public void setDefaultLevel(String defaultValue) {
        String javaLevel = levelsMap.get(defaultValue);
        this.defaultValue = javaLevel != null ? javaLevel : defaultValue;
    }

    @Override
    public void log(String level, String message) {
        String javaLevel = levelsMap.get(level);
        if (javaLevel != null) {
            level = javaLevel;
        }
        logger.log(Level.parse(level.toUpperCase()), message);
    }

}