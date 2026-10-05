package clean_code.singleton;

public class Logger {

    private static Logger logger;

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;
    }
    public void printInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public void printWarn(String message) {
        System.out.println("[WARN] " + message);
    }

    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }
}
