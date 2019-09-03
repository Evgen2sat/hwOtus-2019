package su.medsoft.mis.er.terminal;

import io.micronaut.runtime.Micronaut;

public class Application {

    private static final String LOGGER_NAME = "terminal-api-logger";

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

    public static String getLoggerName() {
        return LOGGER_NAME;
    }
}