package vn.com.vhc.sts.util.exceptions;

public class STS_ConvertException extends Exception {
	private static final long serialVersionUID = 1L;

    private String errorCode;

    public STS_ConvertException(Throwable throwable) {
        super(throwable);
    }

    public STS_ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public STS_ConvertException() {
        super();
    }

    public STS_ConvertException(String message) {
        super(message);
    }

    public STS_ConvertException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public STS_ConvertException(String message, String errorCode,
                            Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
