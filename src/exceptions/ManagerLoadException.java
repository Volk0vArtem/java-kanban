package exceptions;

public class ManagerLoadException extends RuntimeException{
    public ManagerLoadException() {
    }

    public ManagerLoadException(String message) {
        super(message);
    }
}
