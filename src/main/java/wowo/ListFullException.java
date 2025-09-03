package wowo;

/**
 * Thrown when the user wants to add a task into a memory-full list
 */
public class ListFullException extends WowoException {
    public ListFullException() {
        super("COME ON!!! My memory is limited");
    }
}
