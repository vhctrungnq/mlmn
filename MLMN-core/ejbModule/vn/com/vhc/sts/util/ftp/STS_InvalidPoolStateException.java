package vn.com.vhc.sts.util.ftp;

public class STS_InvalidPoolStateException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public STS_InvalidPoolStateException(Throwable throwable) {
        super(throwable);
    }

    public STS_InvalidPoolStateException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public STS_InvalidPoolStateException(String string) {
        super(string);
    }

    public STS_InvalidPoolStateException() {
        super();
    }
}
