package vn.com.vhc.sts.util.exceptions;


public class STS_ImportException extends Exception {
	private static final long serialVersionUID = 1L;

    private String errorCode;

    public STS_ImportException(Throwable throwable) {
        super(throwable);
    }

    public STS_ImportException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public STS_ImportException() {
        super();
    }

    public STS_ImportException(String message) {
        super(message);
    }

    public STS_ImportException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public STS_ImportException(String message, String errorCode,
                           Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
