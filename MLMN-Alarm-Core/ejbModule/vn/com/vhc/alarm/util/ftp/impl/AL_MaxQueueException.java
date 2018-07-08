package vn.com.vhc.alarm.util.ftp.impl;

public class AL_MaxQueueException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public AL_MaxQueueException(Throwable throwable) {
        super(throwable);
    }

    public AL_MaxQueueException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public AL_MaxQueueException(String string) {
        super(string);
    }

    public AL_MaxQueueException() {
        super();
    }
}
