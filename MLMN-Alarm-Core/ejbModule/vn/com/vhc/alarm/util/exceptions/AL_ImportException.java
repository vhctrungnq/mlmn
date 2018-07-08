package vn.com.vhc.alarm.util.exceptions;


public class AL_ImportException extends Exception {
	private static final long serialVersionUID = 1L;

    private String errorCode;

    public AL_ImportException(Throwable throwable) {
        super(throwable);
    }

    public AL_ImportException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AL_ImportException() {
        super();
    }

    public AL_ImportException(String message) {
        super(message);
    }

    public AL_ImportException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AL_ImportException(String message, String errorCode,
                           Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
