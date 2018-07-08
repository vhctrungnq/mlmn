package vn.com.vhc.sts.util.ftp.impl;

public class STS_MaxQueueException extends Exception {
	private static final long serialVersionUID = 1L;
	
    public STS_MaxQueueException(Throwable throwable) {
        super(throwable);
    }

    public STS_MaxQueueException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public STS_MaxQueueException(String string) {
        super(string);
    }

    public STS_MaxQueueException() {
        super();
    }
}
