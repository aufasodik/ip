package wowo;

/**
 * Thrown when the input is not a legal command
 */
public class UnknownCommandException extends WowoException {
    public UnknownCommandException() {
        super("COME ON!!! stop speaking gibberish bro!!");
    }
}
