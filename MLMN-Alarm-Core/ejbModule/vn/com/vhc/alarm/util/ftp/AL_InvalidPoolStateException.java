package vn.com.vhc.alarm.util.ftp;

public class AL_InvalidPoolStateException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public AL_InvalidPoolStateException(Throwable throwable) {
        super(throwable);
    }

    public AL_InvalidPoolStateException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AL_InvalidPoolStateException(String string) {
        super(string);
    }

    public AL_InvalidPoolStateException() {
        super();
    }
}
