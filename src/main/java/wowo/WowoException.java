package wowo;

public class WowoException extends Exception {
    public WowoException(String msg) {
        super(msg);
    }

    public WowoException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
