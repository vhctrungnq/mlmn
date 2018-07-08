package vn.com.vhc.alarm.util.exceptions;

public class AL_ConvertException extends Exception {
	private static final long serialVersionUID = 1L;

    private String errorCode;

    public AL_ConvertException(Throwable throwable) {
        super(throwable);
    }

    public AL_ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public AL_ConvertException() {
        super();
    }

    public AL_ConvertException(String message) {
        super(message);
    }

    public AL_ConvertException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AL_ConvertException(String message, String errorCode,
                            Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
