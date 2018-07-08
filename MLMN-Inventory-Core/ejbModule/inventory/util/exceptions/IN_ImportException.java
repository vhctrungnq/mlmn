package inventory.util.exceptions;


public class IN_ImportException extends Exception {
	private static final long serialVersionUID = 1L;

    private String errorCode;

    public IN_ImportException(Throwable throwable) {
        super(throwable);
    }

    public IN_ImportException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public IN_ImportException() {
        super();
    }

    public IN_ImportException(String message) {
        super(message);
    }

    public IN_ImportException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public IN_ImportException(String message, String errorCode,
                           Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
