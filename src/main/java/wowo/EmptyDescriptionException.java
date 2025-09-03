package wowo;

/**
 * Thrown when user command that requires a non-empty description is not given
 * For example, add command must be followed by a description
 */
public class EmptyDescriptionException extends WowoException {
    public EmptyDescriptionException() {
        super("COME ON!! You know you need todo something");
    }
}
