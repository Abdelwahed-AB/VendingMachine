package exceptions;

public class InvalidCoinException extends RuntimeException{
    public InvalidCoinException() {
        super("Invalid coin");
    }
}
