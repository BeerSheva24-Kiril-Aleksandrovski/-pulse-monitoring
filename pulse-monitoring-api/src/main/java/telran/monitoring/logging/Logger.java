package telran.monitoring.logging;

public interface Logger {
    void setDefaultLevel(String defaultValue);
    void log(String level, String message);

}