package wowo;

public class InvalidTaskIndexException extends WowoException {
    public InvalidTaskIndexException() {
        super("COME ON!!! What do you want me to mark if it does not exist.");
    }
}