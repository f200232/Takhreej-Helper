package CustomException;

public class EmailServiceException extends Exception {
    public EmailServiceException(String message) {
        super(message);
    }
}