package vn.com.vhc.alarm.util.exceptions;

public class AL_ConfigDbFailException extends Exception {
	private static final long serialVersionUID = 1L;

	public AL_ConfigDbFailException(Throwable throwable) {
        super(throwable);
    }

    public AL_ConfigDbFailException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AL_ConfigDbFailException(String string) {
        super(string);
    }

    public AL_ConfigDbFailException() {
        super();
    }
}
