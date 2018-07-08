package inventory.util.exceptions;

public class IN_ConvertException extends Exception {
	private static final long serialVersionUID = 1L;

    private String errorCode;

    public IN_ConvertException(Throwable throwable) {
        super(throwable);
    }

    public IN_ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public IN_ConvertException() {
        super();
    }

    public IN_ConvertException(String message) {
        super(message);
    }

    public IN_ConvertException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public IN_ConvertException(String message, String errorCode,
                            Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
