package vn.com.vhc.alarm.util.task;

public class AL_IllegalServiceStateException extends Throwable {
	private static final long serialVersionUID = 1L;
	
    public AL_IllegalServiceStateException(Throwable throwable) {
        super(throwable);
    }

    public AL_IllegalServiceStateException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AL_IllegalServiceStateException(String string) {
        super(string);
    }

    public AL_IllegalServiceStateException() {
        super();
    }
}
