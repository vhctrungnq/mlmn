package vn.com.vhc.sts.util.exceptions;

public class STS_ConfigDbFailException extends Exception {
	private static final long serialVersionUID = 1L;

	public STS_ConfigDbFailException(Throwable throwable) {
        super(throwable);
    }

    public STS_ConfigDbFailException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public STS_ConfigDbFailException(String string) {
        super(string);
    }

    public STS_ConfigDbFailException() {
        super();
    }
}
