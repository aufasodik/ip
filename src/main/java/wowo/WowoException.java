package wowo;

/**
 * Exception that are thrown by Wowo
 */
public class WowoException extends Exception {
    public WowoException(String msg) {
        super(msg);
    }

    public WowoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}