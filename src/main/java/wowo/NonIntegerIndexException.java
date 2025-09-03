package wowo;

/**
 * Thrown when the index place are replace by a non-integer word
 */
public class NonIntegerIndexException extends WowoException {
    public NonIntegerIndexException() {
        super("COME ON!!! What do you want me to mark?! please input a number");
    }
}
